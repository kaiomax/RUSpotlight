package br.ruspotlight;

import android.content.Intent;
import android.content.res.ColorStateList;
//import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class cardapioActivity extends AppCompatActivity {

    private boolean checked;
    private FloatingActionButton almocou;
    private FloatingActionButton comentar;
    private FloatingActionButton avaliar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        almocou = (FloatingActionButton) findViewById(R.id.almocou);
        comentar = (FloatingActionButton) findViewById(R.id.comentar);
        avaliar = (FloatingActionButton) findViewById(R.id.avaliar);

        checked = false;

        comentar.setVisibility(View.GONE);
        avaliar.setVisibility(View.GONE);

        /*Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Rubik-Regular.tff");
        avaliarBtn.setTypeface(type);
        comentarBtn.setTypeface(type);*/

        almocou.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!checked) {
                    almocou.setImageResource(R.drawable.check);
                    almocou.setBackgroundTintList(ColorStateList.valueOf(0xFF4CAF50));
                    checked = true;
                    almocou.setClickable(false);
                    comentar.setVisibility(View.VISIBLE);
                    avaliar.setVisibility(View.VISIBLE);
                }/*else{
                    almocou.setImageResource(R.drawable.garfofaca);
                    almocou.setBackgroundTintList(ColorStateList.valueOf(0xFF8A8E9C));
                    checked = false;
                }*/
            }
        });
    }

    public void botaoComentar(View view){
        Intent i = new Intent(this, comentariosActivity.class);
        startActivity(i);
    }

    public void setChecked(boolean checked){
        this.checked = true;
    }
}