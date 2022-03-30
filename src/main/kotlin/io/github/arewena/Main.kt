package io.github.arewena

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector


class Main : JavaPlugin(), Listener {

    override fun onEnable() {
        this.server.pluginManager.registerEvents(this, this)
        val dash = ShapedRecipe(NamespacedKey(this, "1"), ItemStack(Material.FEATHER).apply {
            editMeta {
                it.displayName(text("Dash"))
                it.lore(listOf(text("전방을 빠르게 돌진합니다. (20초)")))

            }
        }).apply {
            shape("III", "IBI", "III")
            setIngredient('I', Material.IRON_BOOTS)
            setIngredient('B', Material.BOOK)
        }

        val glow = ShapedRecipe(NamespacedKey(this, "2"), ItemStack(Material.BRICK).apply {
            editMeta {
                it.displayName(text("Down"))
                it.lore(listOf(text("아래로 빠르게 내려갑니다. (낙하 데미지를 받습니다.) (30초)")))

            }
        }).apply {
            shape("AAA", "IBI", "RRR")
            setIngredient('A', Material.ANVIL)
            setIngredient('B', Material.BOOK)
            setIngredient('I', Material.IRON_INGOT)
            setIngredient('R', Material.IRON_BOOTS)

        }

        val sJump = ShapedRecipe(NamespacedKey(this,"3"), ItemStack(Material.RABBIT_FOOT).apply {
            editMeta {
                it.displayName(text("Jump"))
                it.lore(listOf(text("20블럭을 빠르게 점프해 올라갑니다. (30초)")))

            }
        }).apply {
            shape("FFF", "FBF", "III")
            setIngredient('I', Material.IRON_BOOTS)
            setIngredient('B', Material.BOOK)
            setIngredient('F', Material.RABBIT_FOOT)
        }

        val bJump = ShapedRecipe(NamespacedKey(this, "4"), ItemStack(Material.IRON_HORSE_ARMOR).apply {
            editMeta {
                it.displayName(text("bJump"))
                it.lore(listOf(text("뒤로 도약합니다. (30초)")))

            }
        }).apply {
            shape("FFF", "FBF", "FWF")
            setIngredient('W', Material.FIREWORK_ROCKET)
            setIngredient('B', Material.BOOK)
            setIngredient('F', Material.RABBIT_FOOT)
        }
 
        val Slow = ShapedRecipe(NamespacedKey(this, "5"), ItemStack(Material.PHANTOM_MEMBRANE).apply {
            editMeta {
                it.displayName(text("Slow"))
                it.lore(listOf(text("0.3초간 느린 낙하 효과를 얻습니다. (30초)")))

            }
        }).apply {
            shape("PPP", "PBP", "WWW")
            setIngredient('W', Material.FIREWORK_ROCKET)
            setIngredient('B', Material.BOOK)
            setIngredient('P', Material.PHANTOM_MEMBRANE)
        }   


        Bukkit.addRecipe(dash)
        Bukkit.addRecipe(glow)
        Bukkit.addRecipe(bJump)
        Bukkit.addRecipe(sJump)
        Bukkit.addRecipe(Slow)


    }

    @EventHandler
    fun useSpell(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            if (e.item?.type == Material.PHANTOM_MEMBRANE && e.item?.itemMeta!!.displayName() == text("Slow") && !e.player.hasCooldown(Material.PHANTOM_MEMBRANE)) {
                e.player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 6, 100, false))
                e.player.setCooldown(Material.PHANTOM_MEMBRANE, 20 * 30)
            }
        }
            if (e.item?.type == Material.RABBIT_FOOT && e.item?.itemMeta!!.displayName() == text("Jump") && !e.player.hasCooldown(Material.RABBIT_FOOT)) {
                e.player.velocity = Vector(0, 2, 0)
                e.player.setCooldown(Material.RABBIT_FOOT, 20 * 30)
            }

            if (e.item?.type == Material.BRICK && e.item?.itemMeta!!.displayName() == text("Down") && !e.player.hasCooldown(Material.BRICK)) {
                e.player.velocity = Vector(0, -2, 0)
                e.player.setCooldown(Material.BRICK, 20 * 30)
            }

            if (e.item?.type == Material.FEATHER && e.item?.itemMeta!!.displayName() == text("Dash") && !e.player.hasCooldown(Material.FEATHER)) {
                e.player.velocity = e.player.location.direction.multiply(2)
                e.player.setCooldown(Material.FEATHER, 20 * 30)
            }

            if (e.item?.type == Material.IRON_HORSE_ARMOR && e.item?.itemMeta!!.displayName() == text("bJump") && !e.player.hasCooldown(Material.IRON_HORSE_ARMOR)) {
                e.player.velocity = e.player.location.direction.multiply(-3)
                e.player.setCooldown(Material.IRON_HORSE_ARMOR, 20 * 30)
            }

    }



}


