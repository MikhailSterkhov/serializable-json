package org.stonlexx.json;

import lombok.NonNull;

public interface JsonSerializable {

    void serialize(@NonNull JsonMap jsonMap);

    void deserialize(@NonNull JsonMap jsonMap);
}
