package cube;

/**
 * *****************************************************
 * file: Block.java 
 * authors: Tomik Ajhajanian, Arineh Abrahamian, Erick Lopez, Jenna Barrett 
 * class: CS 445 Computer Graphics
 *
 * assignment: Final project Check point 2
 * date last modified: 11/18/2017
 *
 * purpose: Can be one of six types: Grass, Sand, Dirt, Water, Stone, or Bedrock. 
 * Each block has a status of Active or Inactive, and a vector location in 3D space.
 * *****************************************************
 */

public class Block {

    private boolean IsActive;
    private BlockType Type;
    private float x, y, z;

    public enum BlockType {
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        private int BlockID;

        BlockType(int i) {
            BlockID = i;
        }

        public int GetID() {
            return BlockID;
        }

        public void SetID(int i) {
            BlockID = i;
        }
    }
    
    public Block(BlockType type) {
        Type = type;
    }

    //method: setCoords(float, float, float)
    //purpose:Set coordinate position
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //method: SetActive(boolean active)
    //purpose:Set block status to active
    public void SetActive(boolean active) {
        IsActive = active;
    }
    
    //method: isActive()
    //purpose: Return true if active, false otherwise
    public boolean isActive() {
        return IsActive;
    }

    //method: GetID()
    //purpose: Gets the ID
    public int GetID() {
        return Type.GetID();
    }
}
