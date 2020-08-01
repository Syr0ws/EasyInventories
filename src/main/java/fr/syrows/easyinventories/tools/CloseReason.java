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

package fr.syrows.easyinventories.tools;

/**
 * This enum is used to specify the reason of the closure of an inventory.
 */
public enum CloseReason {

    /**
     * Reason used when a inventory is definitely closed.
     */
    CLOSE_ALL,
    /**
     * Reason used when an inventory is closed to open one of its child inventory.
     * It is only used for a TreeInventory.
     */
    OPEN_CHILD,
    /**
     * Reason used when an inventory is closed to open its parent inventory.
     * It is only used for a TreeInventory.
     */
    OPEN_PARENT;
}
