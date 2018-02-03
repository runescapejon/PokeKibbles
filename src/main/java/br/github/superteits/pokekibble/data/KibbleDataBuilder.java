package br.github.superteits.pokekibble.data;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;

import java.util.Optional;

public class KibbleDataBuilder extends AbstractDataBuilder<KibbleData> implements DataManipulatorBuilder<KibbleData, ImmutableKibbleData> {

	public static final int CONTENT_VERSION = 1;
	
	public KibbleDataBuilder() {
		super(KibbleData.class, CONTENT_VERSION);
	}
	
	@Override
	public KibbleData create() {
		return new KibbleData();
	}
	
	@Override
	protected Optional<KibbleData> buildContent(DataView container) {
		if (container.contains(KibbleKeys.IS_KIBBLE.getQuery())) {
			return Optional.of(new KibbleData(container.getBoolean(KibbleKeys.IS_KIBBLE.getQuery()).get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<KibbleData> createFrom(DataHolder dataHolder) {
        return create().fill(dataHolder);
	}

}
