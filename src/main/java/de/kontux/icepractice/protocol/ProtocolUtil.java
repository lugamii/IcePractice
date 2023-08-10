package de.kontux.icepractice.protocol;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class ProtocolUtil {
  public static BlockFace getFaceById(int id) {
    BlockFace face = null;
    switch (id) {
      case 0:
        face = BlockFace.DOWN;
        break;
      case 1:
      case 255:
        face = BlockFace.UP;
        break;
      case 2:
        face = BlockFace.NORTH;
        break;
      case 3:
        face = BlockFace.SOUTH;
        break;
      case 4:
        face = BlockFace.WEST;
        break;
      case 5:
        face = BlockFace.EAST;
        break;
    } 
    return face;
  }
  
  public static Location getLocationAtFace(World world, double x, double y, double z, BlockFace face) {
    return new Location(world, x + face.getModX(), y + face.getModY(), z + face.getModZ());
  }
  
  public static Location getLocationAtFace(Location location, BlockFace face) {
    location.setX(location.getX() + face.getModX());
    location.setY(location.getY() + face.getModY());
    location.setZ(location.getZ() + face.getModZ());
    return location;
  }
}
