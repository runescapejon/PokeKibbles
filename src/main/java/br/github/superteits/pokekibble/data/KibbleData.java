package br.github.superteits.pokekibble.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;

import java.util.Optional;

public class KibbleData extends AbstractBooleanData<KibbleData, ImmutableKibbleData> {

	public KibbleData() {
		this(false);
	}

	public KibbleData(boolean value) {
		super(value, KibbleKeys.IS_KIBBLE, false);
	}

	@Override
	public Optional<KibbleData> fill(DataHolder dataHolder, MergeFunction overlap) {
		dataHolder.get(KibbleData.class).ifPresent(data -> {
			KibbleData finalData = overlap.merge(this, data);
			setValue(finalData.getValue());
		});
		return Optional.of(this);
	}

	@Override
	public Optional<KibbleData> from(DataContainer container) {
		if(container.contains(KibbleKeys.IS_KIBBLE.getQuery())) {
            return Optional.of(new KibbleData(container.getBoolean(KibbleKeys.IS_KIBBLE.getQuery()).get()));
        }
        return Optional.empty();
	}

	@Override
	public KibbleData copy() {
		return new KibbleData(getValue());
	}

	@Override
	public ImmutableKibbleData asImmutable() {
		return new ImmutableKibbleData(getValue());
	}

	@Override
	public int getContentVersion() {
		return KibbleDataBuilder.CONTENT_VERSION;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(KibbleKeys.IS_KIBBLE, getValue());
	}

}
