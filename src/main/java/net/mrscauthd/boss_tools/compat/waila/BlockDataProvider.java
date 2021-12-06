package net.mrscauthd.boss_tools.compat.waila;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.gauge.IGaugeValuesProvider;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineBlockEntity;

public class BlockDataProvider implements IServerDataProvider<BlockEntity>, IComponentProvider {

	public static final BlockDataProvider INSTANCE = new BlockDataProvider();

	@Override
	public void appendServerData(CompoundTag data, ServerPlayer player, Level level, BlockEntity blockEntity, boolean b) {

		List<IGaugeValue> list = new ArrayList<>();

		if (blockEntity instanceof AbstractMachineBlockEntity) {
			AbstractMachineBlockEntity machineBlockEntity = (AbstractMachineBlockEntity) blockEntity;

			IEnergyStorage energyStorage = machineBlockEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null);

			if (energyStorage != null) {
				list.add(GaugeValueHelper.getEnergy(energyStorage));
			}

			list.addAll(machineBlockEntity.getGaugeValues());
			machineBlockEntity.getFluidHandlers().values().stream().map(machineBlockEntity::getFluidHandlerGaugeValues).flatMap(Collection::stream).forEach(list::add);
		}

		if (blockEntity instanceof IGaugeValuesProvider) {
			((IGaugeValuesProvider) blockEntity).getGaugeValues().forEach(list::add);
		}

		WailaPlugin.put(data, WailaPlugin.write(list));
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		WailaPlugin.appendTooltip(tooltip, accessor.getServerData());
	}

}
