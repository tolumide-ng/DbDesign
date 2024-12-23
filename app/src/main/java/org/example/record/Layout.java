package org.example.record;

import static java.sql.Types.INTEGER;
import java.util.HashMap;
import java.util.Map;

import org.example.file.Page;

/**
 * Description of the structure of a record.
 * It contains the name, type, length and offset of 
 * each field of the table.
 */
public class Layout {
    private  Schema schema;
    private Map<String, Integer> offsets;
    private int slotsize;

    /**
     * This constructore create a ayout object from a schema.
     * This constructor is used when a table
     * is created. It determines the physical offset of  each field
     * within the record.
     * @param schema - the schema of the table's record
     */
    public Layout(Schema schema) {
        this.schema = schema;
        this.offsets = new HashMap<>();
        int pos = Integer.BYTES; // leave space for the empty/inuse flag
        for (String fldname: schema.fields()) {
            offsets.put(fldname, pos);
            pos += lengthInBytes(fldname);
        }
        slotsize = pos;
    }

    /**
     * Create a Layout object from the specified metadata.
     * This constructor is used when the metadata
     * is retrieved from the catalog.
     * @param schema - the schema of the table's record
     * @param offsets - the already calculated offsets of the fields within a record
     * @param recordlen - the already calculated length of each record
     */
    public Layout(Schema schema, Map<String,Integer> offsets, int slotsize) {
        this.schema = schema;
        this.offsets = offsets;
        this.slotsize = slotsize;
    }

    /**
     * Returns the schema of the table's records
     * @return the table's record schema
     */
    public Schema schema() { 
        return schema;
    }

    /**
     * Return the offset of a specified field within a record
     * @param fldname - the name of the field
     * @return - the offset of that field within a record
     */
    public int offset(String fldname) {
        return offsets.get(fldname);
    }

    /**
     * Return the size of a slot in bytes.
     * @return the size of a slot
     */
    public int slotSize() {
        return slotsize;
    }

    private int lengthInBytes(String fldname) {
        int fldtype = schema.type(fldname);
        if (fldtype == INTEGER)
            return Integer.BYTES;
        else // fldtype == VARCHAR
            return Page.maxLength(schema.length(fldname));
    }

}
