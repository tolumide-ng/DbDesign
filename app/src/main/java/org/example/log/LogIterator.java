package org.example.log;

import java.util.Iterator;

import org.example.file.BlockId;
import org.example.file.FileMgr;
import org.example.file.Page;

class LogIterator implements Iterator<byte[]> {
    private FileMgr fm;
    private Page p;
    private int currentpos;
    private int boundary;
    private BlockId blk;


    /**
     * Creates an iterator for the records in the log file,
     * positioned after the last record.
     */
    public LogIterator(FileMgr fm, BlockId blk) {
        this.fm = fm;
        this.blk = blk;
        byte[] b = new byte[fm.blockSize()];
        p = new Page(b);
        moveToBlock(blk);
    }

    /*
     * Determines if the current log is the earliest reocrd in the log file.
     * @return true if there is an earlier record
     */
    public boolean hasNext() {
        return (currentpos < fm.blockSize()) || (blk.number() > 0);
    }


    public byte[] next() {
        if (currentpos == fm.blockSize()) {
            blk = new BlockId(blk.fileName(), blk.number()-1);
            moveToBlock(blk);
        }
        byte[] rec = p.getBytes(currentpos);
        currentpos += Integer.BYTES + rec.length;
        return rec;
    }

    /**
     * Moves to the specified log block and positons
     * it at the first record in that block (i.e, the most recent one).
     */
    private void moveToBlock(BlockId blk) {
        fm.read(blk, p);
        boundary = p.getInt(0);
        currentpos = boundary;
    }
}
