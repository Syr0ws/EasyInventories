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

package fr.syrows.easyinventories.iterators;

import fr.syrows.easyinventories.iterators.impl.HorizontalIterator;
import fr.syrows.easyinventories.iterators.impl.VerticalIterator;

public enum SlotIteratorEnum implements SlotIteratorType {

    HORIZONTAL(HorizontalIterator.class), VERTICAL(VerticalIterator.class);

    private Class<? extends SlotIterator> type;

    SlotIteratorEnum(Class<? extends SlotIterator> type) {
        this.type = type;
    }

    public Class<? extends SlotIterator> getIteratorClass() {
        return this.type;
    }
}
