/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.configuration;

import java.nio.file.Path;

public class ConfigDataContainer {

    private String data; // Data of the resource file.
    private Path path;

    public ConfigDataContainer(String data) {
        this.data = data;
    }

    public ConfigDataContainer(Path path) {
        this.path = path;
    }

    public ConfigDataContainer(String data, Path path) {
        this.data = data;
        this.path = path;
    }

    public String getResourceData() {
        return this.data;
    }

    public boolean hasResourceData() {
        return this.data != null;
    }

    public Path getPath() {
        return this.path;
    }

    public boolean hasPath() {
        return this.path != null;
    }
}
