package com.josroossien.arend.runnables;

import com.josroossien.arend.AREnd;
import com.josroossien.arend.util.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class DragonRunnable extends BukkitRunnable {
	
	private AREnd are;
	private EnderDragon dragon = null;
	
	public DragonRunnable(AREnd are) {
		this.are = are;
	}
	
	
	@Override
	public void run() {
		if (dragon == null || dragon.isDead() || !dragon.isValid()) {
			this.dragon = getDragon();
		}
		if (dragon == null) {
			return;
		}
		Player target = null;
		List<Entity> near = dragon.getNearbyEntities(100.0D, 100.0D, 100.0D);
		for(Entity entity : near) {
		    if(entity instanceof Player) {
		    	if (((Player)entity).getGameMode() == GameMode.SURVIVAL) {
		    		target = (Player) entity;
			        break;
		    	}
		    }
		}
		if (target == null) {
			return;
		}
		if (!dragon.hasLineOfSight(target)) {
			return;
		}
		
		double vx = target.getLocation().getX() - dragon.getLocation().getX();
        double vy = target.getLocation().getY() - 2.0D - dragon.getLocation().getY();
        double vz = target.getLocation().getZ() - dragon.getLocation().getZ();
        
		double angle = dragon.getVelocity().angle(new Vector(vx, vy, vz));
		
		if (angle <= 1.5D /* 0.7853981633974501D*/) {
			Utils.shootAt(target, EntityType.FIREBALL, dragon.getLocation().add(0.0d, -3.5d, 0.0d), 5.0f);
		}
	}


	private EnderDragon getDragon() {
		List<Entity> entities = are.getServer().getWorld("world_the_end").getEntities();
		for (Entity e : entities) {
			if (e instanceof EnderDragon) {
				return (EnderDragon)e;
			}
		}
		return null;
	}
}
