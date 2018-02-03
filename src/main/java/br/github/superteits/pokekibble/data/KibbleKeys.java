package br.github.superteits.pokekibble.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;

public class KibbleKeys {
	
	public static Key<Value<Boolean>> IS_KIBBLE = Key.builder()
			.type(new TypeToken<Value<Boolean>>() {})
			.id("is_kibble")
			.name("IsKibble")
			.query(DataQuery.of("IsKibble"))
			.build();
}
