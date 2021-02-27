package com.dojo.agendasimples;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    EditText textoNome, textoNumero, textoEndereco, textoEmail;
    ImageView imagemContato;
    ListView listaDeContatos;
    TextView textoTitulo;
    Button botaoAdicionar;
    Uri imagemUri = Uri.parse("android.resource://titopetri.com.agendinhanova/drawable/ic_user.png");
    long longClickedItemId;
    CursorAdapter contactAdapter;
    TabHost tabHost;

    private static final int EDIT = 0, DELETE = 1;
    public static final int TAB_CREATOR = 0;
    public static final int TAB_CONTACT_LIST = 1;

    //Contato updatingContact;
    //DatabaseHandler dbHandler;

    boolean fotoCamera;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        textoNome = (EditText) findViewById(R.id.campoNome);
        textoNumero = (EditText) findViewById(R.id.campoNumero);
        textoEndereco = (EditText) findViewById(R.id.campoEndereco);
        textoEmail = (EditText) findViewById(R.id.campoEmail);
        listaDeContatos = (ListView) findViewById(R.id.listaContatos);
        imagemContato = (ImageView) findViewById(R.id.imagemContato);
        textoTitulo = (TextView) findViewById(R.id.textoTitulo);
        botaoAdicionar = (Button) findViewById(R.id.botaoAdicionarContato);

        textoTitulo.setText("Adicionar Contato");
        botaoAdicionar.setText("Adicionar");

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("adicionar");
        tabSpec.setContent(R.id.layoutTelaAdicionar);
        tabSpec.setIndicator("Adicionar");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("agenda");
        tabSpec.setContent(R.id.layoutTelaContatos);
        tabSpec.setIndicator("Agenda");
        tabHost.addTab(tabSpec);

        //dbHandler = new DatabaseHandler(getApplicationContext());

        registerForContextMenu(listaDeContatos);
        listaDeContatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemId = id;
                return false;
            }
        });

        popularLista();
    }

    public void clicaCarregarImagem(View v){
        fotoCamera=false;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
    }

    public void clicaAdicionarContato(View v){

        boolean success;
        String message;
        /*
        if (updatingContact != null) {
            updatingContact.setNome(textoNome.getText().toString());
            updatingContact.setTelefone(textoNumero.getText().toString());
            updatingContact.setEndereco(textoEndereco.getText().toString());
            updatingContact.setEmail(textoEmail.getText().toString());
            updatingContact.setImageURI(imagemUri);

            success = dbHandler.updateContact(updatingContact);
            message = String.format("%s foi atualizado com sucesso!", updatingContact.getName());

            updatingContact = null;
            textoTitulo.setText("Adicionar Contato");
            botaoAdicionar.setText("Adicionar");
        } else {
            Contato contato = new Contato(String.valueOf(textoNome.getText()), String.valueOf(textoNumero.getText()), String.valueOf(textoEmail.getText()), String.valueOf(textoEndereco.getText()), imagemUri);

            success = dbHandler.createContact(contato);
            message = String.format("%s foi adicionado aos contatos!", contato.getName());
        }

        if (success) {
            tabHost.setCurrentTab(TAB_CONTACT_LIST);
            popularLista();
            limparDados();

        } else {
            message = "ocorreu um erro na operação";
        }*/

        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    /*
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.ic_edit);
        menu.setHeaderTitle("Menu");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Editar Contato");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Deletar Contacto");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT:
                updatingContact = dbHandler.getContact(longClickedItemId);

                textoNome.setText(updatingContact.getName());
                textoNumero.setText(updatingContact.getPhone());
                textoEmail.setText(updatingContact.getEmail());
                textoEndereco.setText(updatingContact.getAddress());
                imagemContato.setImageURI(updatingContact.getImageURI());

                tabHost.setCurrentTab(TAB_CREATOR);

                textoTitulo.setText("Editar Contato");
                botaoAdicionar.setText("Concluir Edicao");
                break;
            case DELETE:
                dbHandler.deleteContact(longClickedItemId);
                popularLista();
                break;
        }
        return super.onContextItemSelected(item);
    }

    */
    private void popularLista() {
        //contactAdapter = dbHandler.getSimpleCursorAdapter(this);
        //listaDeContatos.setAdapter(contactAdapter);
    }

    public void limparDados() {
        textoNome.setText("");
        textoNumero.setText("");
        textoEmail.setText("");
        textoEndereco.setText("");
        imagemContato.setImageResource(R.drawable.ic_user_foreground);
        imagemContato.setRotation(0);
    }

    public void clicaTirarFoto(View v){
        fotoCamera = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (fotoCamera) {
            super.onActivityResult(requestCode, resultCode, data);
            InputStream stream = null;
            if (requestCode == 0 && resultCode == RESULT_OK) {
                try {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    imagemContato.setImageBitmap(resizeImage(this, bitmap, 160, 160));
                    imagemContato.setRotation(90);
                    imagemUri = data.getData();
                    imagemContato.setImageURI(data.getData());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (stream != null)
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

            }
        }else{
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    imagemUri = data.getData();
                    imagemContato.setImageURI(data.getData());
                }
            }
        }
    }

    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeight) {
        Bitmap novoBmp = null;
        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();
        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;
        //Calcula escala em percentagem do tamanho original para o novo tamanho
        float scalaW = novoW / w;
        float scalaH = novoH / h;
        // Criando uma matrix para manipulação da imagem BitMap
        Matrix matrix = new Matrix();
        // Definindo a proporção da escala para o matrix
        matrix.postScale(scalaW, scalaH);
        //criando o novo BitMap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);
        return novoBmp;
    }
}