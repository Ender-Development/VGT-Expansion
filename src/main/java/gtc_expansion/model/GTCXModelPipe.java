package gtc_expansion.model;

import gtc_expansion.util.GTCXHelperPipe;
import gtclassic.api.block.GTBlockBaseConnect;
import ic2.core.RotationList;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.Ic2Models;
import ic2.core.platform.textures.models.BaseModel;
import ic2.core.util.helpers.BlockStateContainerIC2.IC2BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GTCXModelPipe extends BaseModel {
    List<BakedQuad>[] quads; // All possible connection configurations
    List<BakedQuad>[] anchorQuads = this.createList(64); // All possible anchor configurations
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Map<Integer, List<BakedQuad>> comboQuads = new HashMap();// A sum of the above quad lists
    IBlockState state;
    // functions
    int[] sizes; // This is an int array to draw the size of the cable, [0, 16] would be a full
    // block, [4, 12] would be half a block.
    final TextureAtlasSprite anchorBackTexture = Ic2Icons.getTextures("gtclassic_terrain")[1];
    public GTCXModelPipe(IBlockState block, int[] sizes) {
        super(Ic2Models.getBlockTransforms());
        this.state = block;
        this.sizes = sizes;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void init() {
        GTBlockBaseConnect wire = (GTBlockBaseConnect) this.state.getBlock();
        //IGTCoverBlock pipe = (IGTCoverBlock) this.state.getBlock();
        quads = this.createList(64);
        this.setParticalTexture(wire.getParticleTexture(this.state));
        int min = this.sizes[0];// low size
        int max = this.sizes[1];// high size
        Map<EnumFacing, List<BakedQuad>> anchorQuadList = new EnumMap(EnumFacing.class);
        Map<EnumFacing, BakedQuad> coreQuads = this.generateCoreQuads(wire, min, max);
        Map<EnumFacing, List<BakedQuad>> sideQuads = new EnumMap(EnumFacing.class);
        EnumFacing[] facings = EnumFacing.VALUES;
        int facingsLength = facings.length;
        for (int i = 0; i < facingsLength; ++i) {
            EnumFacing side = facings[i];
            sideQuads.put(side, this.generateQuadsForSide(wire, side, min, max));
            anchorQuadList.put(side, this.generateQuadsForAnchor(anchorBackTexture, side, 0, 16));
        }

        for (int j = 0; j < 64; ++j) {
            RotationList rotation = RotationList.ofNumber(j);
            List<BakedQuad> quadList = this.quads[j];
            Iterator rotations = rotation.iterator();
            EnumFacing side;
            while (rotations.hasNext()) {
                side = (EnumFacing) rotations.next();
                quadList.addAll(sideQuads.get(side));
                this.anchorQuads[j].addAll(anchorQuadList.get(side));
            }
            rotations = rotation.invert().iterator();
            while (rotations.hasNext()) {
                side = (EnumFacing) rotations.next();
                quadList.add(coreQuads.get(side));
            }
        }
    }

    // Merges core and anchor quads to create all quads
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side == null) {
            if (!(state instanceof IC2BlockState)) {
                // if its in jei/creative tab it will default to the int passed below, 12 =
                // (4+8) (north+south)
                return this.quads[12];
            } else {
                GTCXHelperPipe.GTCXQuadWrapper wrapper = ((IC2BlockState)state).getData();
                Vec3i vec = wrapper.getVec();
                if (vec.getY() > 0) {
                    List<BakedQuad> list = this.comboQuads.get(vec.getZ());
                    if (list == null) {
                        list = new ArrayList(this.quads[vec.getX()]);
                        list.addAll(this.anchorQuads[vec.getY()]);
                        this.comboQuads.put(vec.getZ(), list);
                    }
                    return list;
                } else {
                    return this.quads[vec.getX()];
                }
            }
        } else {
            if (state instanceof IC2BlockState){
                GTCXHelperPipe.GTCXQuadWrapper wrapper = ((IC2BlockState)state).getData();
                QuadList quadList = wrapper.getQuadList();
                return quadList.getQuads(side);
            }
            return this.getEmptyList();
        }
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return PerspectiveMapWrapper.handlePerspective(this, this.getCamera(), cameraTransformType);
    }

    // This is where the basic/core model quads are generated
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Map<EnumFacing, BakedQuad> generateCoreQuads(GTBlockBaseConnect wire, int min, int max) {
        Vector3f minF = new Vector3f((float) min, (float) min, (float) min);
        Vector3f maxF = new Vector3f((float) max, (float) max, (float) max);
        BlockPartFace face = new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] { (float) min,
                (float) min, (float) max, (float) max }, 0));
        Map<EnumFacing, BakedQuad> quads = new EnumMap(EnumFacing.class);
        EnumFacing[] var8 = EnumFacing.VALUES;
        int var9 = var8.length;
        for (int var10 = 0; var10 < var9; ++var10) {
            EnumFacing side = var8[var10];
            TextureAtlasSprite sprite = this.getParticleTexture();
            quads.put(side, this.getBakery().makeBakedQuad(minF, maxF, face, sprite, side, ModelRotation.X0_Y0, null, true, true));
        }
        return quads;
    }

    // This is where anchor quads are generated
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<BakedQuad> generateQuadsForAnchor(TextureAtlasSprite sprite, EnumFacing facing, int min, int max) {
        List<BakedQuad> quads = new ArrayList();
        Pair<Vector3f, Vector3f> position = this.getPosForSide(facing, min, max);
        EnumFacing[] var7 = EnumFacing.VALUES;
        int var8 = var7.length;
        for (int var9 = 0; var9 < var8; ++var9) {
            EnumFacing side = var7[var9];
            BlockPartFace face = null;
            // Below these just resize the texture of the anchor but not the side of the
            // actual quads
            if (side == facing || side == facing.getOpposite()) {
                face = new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { (float) min,
                        (float) min, (float) max, (float) max }, 0));
            } else if (facing.getAxis() == EnumFacing.Axis.Z && side.getAxis() == EnumFacing.Axis.X) {
                face = new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { (float) max,
                        (float) min, 16.0F, (float) max }, 0));
            } else {
                face = this.getFace(facing, min, max, -1);
            }
            // If you would like a different texture for anchors, change the sprite var to
            // what you want, by default its passing the sprite in the model constructor
            quads.add(this.getBakery().makeBakedQuad(position.getKey(), position.getValue(), face, sprite, side, ModelRotation.X0_Y0, null, true, true));
        }
        return quads;
    }

    // This is where the sides connected to things are generated
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<BakedQuad> generateQuadsForSide(GTBlockBaseConnect wire, EnumFacing facing, int min, int max) {
        List<BakedQuad> quads = new ArrayList();
        Pair<Vector3f, Vector3f> position = this.getPosForSide(facing, min, max);
        EnumFacing[] facings = EnumFacing.VALUES;
        int facingLength = facings.length;
        for (int i = 0; i < facingLength; ++i) {
            EnumFacing side = facings[i];
            if (side.getOpposite() != facing) {
                BlockPartFace face = null;
                if (side == facing) {
                    face = new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] { (float) min,
                            (float) min, (float) max, (float) max }, 0));
                } else if (facing.getAxis() == EnumFacing.Axis.Z && side.getAxis() == EnumFacing.Axis.X) {
                    face = new BlockPartFace(null, 0, "", new BlockFaceUV(new float[] { (float) max,
                            (float) min, 16.0F, (float) max }, 0));
                } else {
                    face = this.getFace(facing, min, max, 0);
                }
                // If you would like a different texture for connected sides, change the sprite
                // var to what you want
                TextureAtlasSprite sprite = wire.getTextureFromState(this.state, side);
                quads.add(this.getBakery().makeBakedQuad(position.getKey(), position.getValue(), face, sprite, side, ModelRotation.X0_Y0, null, true, true));
            }
        }
        return quads;
    }

    private Pair<Vector3f, Vector3f> getPosForSide(EnumFacing facing, int min, int max) {
        switch (facing) {
            case DOWN:
                return Pair.of(new Vector3f(min, 0.0F, min), new Vector3f(max, min, max));
            case UP:
                return Pair.of(new Vector3f(min, max, min), new Vector3f(max, 16.0F, max));
            case NORTH:
                return Pair.of(new Vector3f(min, min, 0.0F), new Vector3f(max, max, min));
            case SOUTH:
                return Pair.of(new Vector3f(min, min, max), new Vector3f(max, max, 16.0F));
            case WEST:
                return Pair.of(new Vector3f(0.0F, min, min), new Vector3f(min, max, max));
            case EAST:
                return Pair.of(new Vector3f(max, min, min), new Vector3f(16.0F, max, max));
        }
        return Pair.of(new Vector3f(min, min, min), new Vector3f(max, max, max));
    }

    // The zeros passed as the second arg in the BlockPartFace constructor make it
    // so the face is colorable, make it -1 if you dont want it to be colored
    private BlockPartFace getFace(EnumFacing facing, int min, int max, int index) {
        switch (facing) {
            case DOWN:
            case SOUTH:
                return new BlockPartFace(null, index, "", new BlockFaceUV(new float[] { min, max, max, 16.0F }, 0));
            case UP:
            case NORTH:
                return new BlockPartFace(null, index, "", new BlockFaceUV(new float[] { min, 0.0F, max, min }, 0));
            case WEST:
                return new BlockPartFace(null, index, "", new BlockFaceUV(new float[] { 0.0F, min, min, max }, 0));
            case EAST:
                return new BlockPartFace(null, index, "", new BlockFaceUV(new float[] { max, min, 16.0F, max }, 0));
        }
        return new BlockPartFace(null, index, "", new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0));
    }

    private BlockPartFace getUntintedFace(EnumFacing facing, int min, int max) {
        switch (facing) {
            case DOWN:
            case SOUTH:
                return new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { min, max, max, 16.0F }, 0));
            case UP:
            case NORTH:
                return new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { min, 0.0F, max, min }, 0));
            case WEST:
                return new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { 0.0F, min, min, max }, 0));
            case EAST:
                return new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { max, min, 16.0F, max }, 0));
        }
        return new BlockPartFace(null, -1, "", new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0));
    }

    @SideOnly(Side.CLIENT)
    public static class QuadList {
        List<BakedQuad>[] quads = createList();

        public QuadList() {
        }

        public void addQuads(List<BakedQuad> list, EnumFacing facing) {
            this.quads[facing.getIndex()].addAll(list);
        }

        public List<BakedQuad> getQuads(EnumFacing facing) {
            return this.quads[facing.getIndex()];
        }

        protected List<BakedQuad>[] createList() {
            List<BakedQuad>[] quads = new List[6];

            for(int i = 0; i < 6; ++i) {
                quads[i] = new ArrayList<>();
            }

            return quads;
        }
    }
}
