/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.spi.type;

import io.prestosql.spi.block.Block;
import io.prestosql.spi.block.BlockBuilder;
import io.prestosql.spi.connector.ConnectorSession;

import static io.airlift.slice.SizeOf.SIZE_OF_LONG;
import static io.prestosql.spi.type.TypeSignature.parseTypeSignature;

public class TestingIdType
        extends AbstractFixedWidthType
{
    public static final TestingIdType ID = new TestingIdType();
    public static final String NAME = "id";

    private TestingIdType()
    {
        super(parseTypeSignature(NAME), long.class, SIZE_OF_LONG);
    }

    @Override
    public boolean isComparable()
    {
        return true;
    }

    @Override
    public Object getObjectValue(ConnectorSession session, Block block, int position)
    {
        if (block.isNull(position)) {
            return null;
        }

        return block.getLong(position, 0);
    }

    @Override
    public boolean equalTo(Block leftBlock, int leftPosition, Block rightBlock, int rightPosition)
    {
        long leftValue = leftBlock.getLong(leftPosition, 0);
        long rightValue = rightBlock.getLong(rightPosition, 0);
        return leftValue == rightValue;
    }

    @Override
    public long hash(Block block, int position)
    {
        return block.getLong(position, 0);
    }

    @Override
    public void appendTo(Block block, int position, BlockBuilder blockBuilder)
    {
        if (block.isNull(position)) {
            blockBuilder.appendNull();
        }
        else {
            blockBuilder.writeLong(block.getLong(position, 0)).closeEntry();
        }
    }

    @Override
    public long getLong(Block block, int position)
    {
        return block.getLong(position, 0);
    }

    @Override
    public void writeLong(BlockBuilder blockBuilder, long value)
    {
        blockBuilder.writeLong(value).closeEntry();
    }
}
