package nl.apps.mprog.client.gui;

import java.util.List;

import nl.apps.mprog.client.R;
import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Grid {
	
	public Context context;
	public Cell[][] cells;
	
	public Grid(Context c){
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
					
					cells[i-1][j-1] = new Cell(i-1, j-1, b);
					 
					tr.addView(b);
				}				 
			}
		
			tl.addView(tr,new TableLayout.LayoutParams(
				 	LayoutParams.FILL_PARENT,
				 	LayoutParams.WRAP_CONTENT));
		}
		
		cells[3][3].changeState(State.LIGHT);
		cells[4][4].changeState(State.LIGHT);
		cells[3][4].changeState(State.DARK);
		cells[4][3].changeState(State.DARK);
	}
	
	public List<Cell> getAvailableMoves(Cell cell){
		return null;
	}
	
}
