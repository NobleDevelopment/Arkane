package org.nobledev.arkane.adapter.ext

import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.NPC
import net.citizensnpcs.api.trait.trait.Equipment
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

fun createNPC(name: String, type: EntityType = EntityType.PLAYER, builder: NPC.() -> Unit) =
    CitizensAPI.getNPCRegistry().createNPC(type, name).apply(builder)

fun Entity.isNPC(): Boolean = hasMetadata("NPC")

fun NPC.setEquipment(slot: Equipment.EquipmentSlot, item : ItemStack) {
    this.getOrAddTrait(Equipment::class.java).set(slot, item)
}
fun test() {
    val npc = createNPC("TridiumX") {

    }
}

