package com.example.buscarinformacion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText txt_dni;
    Button btn_buscar;
    TextView txt_nombres;
    String resultado;
    String cambiar1;
    String [] cambiar2;
    String cambiar3="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_dni = findViewById(R.id.txt_dni);
        btn_buscar = findViewById(R.id.btn_buscar);
        txt_nombres = findViewById(R.id.txt_nombres);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar1 = Buscar_dni(txt_dni.getText().toString());
                cambiar2 = cambiar1.split(Pattern.quote("|"));
                for (int i = 0; i < cambiar2.length; i++){
                    cambiar3=cambiar3+" "+cambiar2[i];
                }
                txt_nombres.setText(cambiar3);
            }
        });
    }

    public String Buscar_dni(String dni){
        try{
            URL url = new URL ("http://aplicaciones007.jne.gob.pe/srop_publico/Consulta/Afiliado/GetNombresCiudadano?DNI="+dni);
            URLConnection con = null;
            con = url.openConnection();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream(),"ISO-8859-1"));
            StringBuilder sb = new StringBuilder();
            try{
                String s;
                while((s = r.readLine()) != null){
                    sb.append(s);
                    sb.append("\n");
                }
            }finally {
                r.close();
            }
            resultado = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
