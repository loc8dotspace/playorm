package com.alvazan.orm.layer5.query;

import com.alvazan.orm.api.z5api.IndexColumnInfo;
import com.alvazan.orm.api.z8spi.conv.ByteArray;
import com.alvazan.orm.api.z8spi.iter.AbstractCursor.Holder;
import com.alvazan.orm.api.z8spi.iter.DirectCursor;
import com.alvazan.orm.api.z8spi.meta.ViewInfo;

public class CursorForAnd implements DirectCursor<IndexColumnInfo> {

	private DirectCursor<IndexColumnInfo> leftResults;
	private DirectCursor<IndexColumnInfo> rightResults;
	private ViewInfo leftView;
	private ViewInfo rightView;
	
	public CursorForAnd(ViewInfo leftView2, DirectCursor<IndexColumnInfo> leftResults,
			ViewInfo rightView2, DirectCursor<IndexColumnInfo> rightResults) {
		this.leftView = leftView2;
		this.rightView = rightView2;
		this.leftResults = leftResults;
		this.rightResults = rightResults;
	}

	@Override
	public Holder<IndexColumnInfo> nextImpl() {
		while(true) {
			Holder<IndexColumnInfo> nextFromCursor = leftResults.nextImpl();
			if(nextFromCursor == null)
				break;
			IndexColumnInfo next = nextFromCursor.getValue();
		
			//Need to change to lookahead joining here as well as switch join types on the fly
			//This stinks as we have to re-read from the database every 1000 rows!!!! We should find out if
			//we can do any kind of counting or something so we don't have to go back to database every time
			rightResults.beforeFirst();
			Holder<IndexColumnInfo> result = runInnerLoop(next);
			if(result != null)
				return result;
		}
		return null;
	}

	private Holder<IndexColumnInfo> runInnerLoop(IndexColumnInfo next) {
		while(true) {
			Holder<IndexColumnInfo> nextFromCursor = rightResults.nextImpl();
			if(nextFromCursor == null)
				break;
			IndexColumnInfo andedInfo = nextFromCursor.getValue();
			ByteArray key1 = next.getPrimaryKey(leftView);
			ByteArray key2 = andedInfo.getPrimaryKey(rightView);
			if(key1.equals(key2)) {
				next.mergeResults(andedInfo);
				return new Holder<IndexColumnInfo>(next);
			}
		}
		return null;
	}


	@Override
	public void beforeFirst() {
		leftResults.beforeFirst();
		rightResults.beforeFirst();
	}

}
