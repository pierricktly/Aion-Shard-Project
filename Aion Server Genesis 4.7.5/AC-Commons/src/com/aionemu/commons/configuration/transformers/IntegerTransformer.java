/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, regarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Transfomrs string to integer. Integer can be represented both as decimal or
 * hex value.
 *
 * @author SoulKeeper
 */
public class IntegerTransformer implements PropertyTransformer<Integer> {

    /**
     * Shared instance of this transformer. It's thread-safe so no need of
     * multiple instances
     */
    public static final IntegerTransformer SHARED_INSTANCE = new IntegerTransformer();

    /**
     * Transforms value to integer
     *
     * @param value value that will be transformed
     * @param field value will be assigned to this field
     * @return Integer object that represents value
     * @throws TransformationException if something went wrong
     */
    @Override
    public Integer transform(String value, Field field) throws TransformationException {
        try {
            return Integer.decode(value);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
