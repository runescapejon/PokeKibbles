package br.github.superteits.pokekibble.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableKibbleData extends AbstractImmutableBooleanData<ImmutableKibbleData, KibbleData> {

	public ImmutableKibbleData() {
		this(false);
	}

	public ImmutableKibbleData(boolean value) {
		super(value, KibbleKeys.IS_KIBBLE, false);
	}

	public ImmutableValue<Boolean> kibble() {
		return this.getValueGetter();
	}

	@Override
	public KibbleData asMutable() {
		return new KibbleData(this.getValue());
	}

	@Override
	public int getContentVersion() {
		return KibbleDataBuilder.CONTENT_VERSION;
	}

	@Override
	public DataContainer toContainer() {
        return super.toContainer().set(KibbleKeys.IS_KIBBLE, this.getValue());
	}

}
