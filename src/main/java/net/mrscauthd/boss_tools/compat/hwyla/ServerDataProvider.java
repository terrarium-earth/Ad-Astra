package net.mrscauthd.boss_tools.compat.hwyla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueSerializer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineBlockEntity;

public class ServerDataProvider /* implements IServerDataProvider<TileEntity>*/ {
/*
	public static final ResourceLocation DATA_KEY = new ResourceLocation(BossToolsMod.ModId, "datakey");
	public static final ServerDataProvider INSTANCE = new ServerDataProvider();

	public static ListNBT write(List<IGaugeValue> list) {
		ListNBT nbt = new ListNBT();
		list.stream().map(GaugeValueSerializer.Serializer::serialize).forEach(nbt::add);
		return nbt;
	}

	public static List<IGaugeValue> read(ListNBT nbt) {
		List<IGaugeValue> list = new ArrayList<>();
		nbt.stream().map(h -> (CompoundNBT) h).map(GaugeValueSerializer.Serializer::deserialize).forEach(list::add);
		return list;
	}

	public static ListNBT get(CompoundNBT compound) {
		return compound.getList(DATA_KEY.toString(), NBT.TAG_COMPOUND);
	}

	public static void put(CompoundNBT compound, ListNBT nbt) {
		compound.put(DATA_KEY.toString(), nbt);
	}

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity t) {
		if (t instanceof AbstractMachineTileEntity) {
			AbstractMachineTileEntity machineTileEntity = (AbstractMachineTileEntity) t;

			IEnergyStorage energyStorage = machineTileEntity.getCapability(CapabilityEnergy.ENERGY).orElse(null);
			List<IGaugeValue> list = new ArrayList<>();

			if (energyStorage != null) {
				list.add(GaugeValueHelper.getEnergy(energyStorage));
			}

			list.addAll(machineTileEntity.getGaugeValues());
			machineTileEntity.getFluidHandlers().values().stream().map(machineTileEntity::getFluidHandlerGaugeValues).flatMap(Collection::stream).forEach(list::add);
			
			put(data, write(list));
		}
	}

 */
}
