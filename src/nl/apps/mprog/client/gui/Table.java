package nl.apps.mprog.client.gui;

import nl.apps.mprog.client.R;
import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Table {
	
	public Context context;
	public Cell[][] cells;
	
	public Table(Context c){
		this.context = c;
		this.cells = new Cell[8][8];
	}

	public void spawn(){
		TableLayout tl = (TableLayout) ((Activity) context).findViewById(R.id.table);
		
		for(int j = 0; j < 9; j++){
		
			TableRow tr = new TableRow(context);
			
			tr.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
			    LayoutParams.WRAP_CONTENT));
			
			for(int i = 0; i < 9; i++){
				
				if (j == 0 || i == 0) {
					
					TextView textview = new TextView(context);
					textview.setLayoutParams(new LayoutParams(
				            LayoutParams.FILL_PARENT,
				            LayoutParams.WRAP_CONTENT));
					
					if(i == 0 && j == 0){
						textview.setText(" ");
					} else if (i == 0){
						textview.setText(String.valueOf(j));
					} else {
						textview.setText(String.valueOf((char) (i+96)));
					}
					
					tr.addView(textview);
				} else {
				
					Button b = new Button(context);
		
					b.setText("e");
					 
					b.setLayoutParams(new LayoutParams(
					    LayoutParams.FILL_PARENT,
					    LayoutParams.WRAP_CONTENT));
					
					Cell cell = new Cell(i-1, j-1, b);
					cells[i-1][j-1] = cell;
					 
					tr.addView(b);
				}				 
			}
		
			tl.addView(tr,new TableLayout.LayoutParams(
				 	LayoutParams.FILL_PARENT,
				 	LayoutParams.WRAP_CONTENT));
		}
	}
}
