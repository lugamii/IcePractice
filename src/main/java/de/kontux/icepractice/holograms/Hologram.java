package de.kontux.icepractice.holograms;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Hologram {
  private final String[] lines;
  
  private final List<Entity> entities = new ArrayList<>();
  
  public Hologram(String... lines) {
    this.lines = lines;
  }
  
  public void create(Location start) {
    for (int i = 0; i < this.lines.length; i++) {
      Location location = start.subtract(0.0D, i + 0.3D, 0.0D);
      ArmorStand armorStand = (ArmorStand)location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
      armorStand.setCustomName(this.lines[i]);
      armorStand.setCustomNameVisible(true);
      armorStand.setArms(false);
      armorStand.setGravity(false);
      armorStand.setVisible(false);
      this.entities.add(armorStand);
    } 
  }
  
  public void remove() {
    for (Entity entity : this.entities)
      entity.remove(); 
  }
}
