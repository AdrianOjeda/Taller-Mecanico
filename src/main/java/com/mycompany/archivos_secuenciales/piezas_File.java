package com.mycompany.archivos_secuenciales;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class piezas_File {
    private RandomAccessFile file;
    private String path = "C:\\Proyecto\\piezas.dat"; // Changed to .dat for RandomAccessFile

    Object[][] datos = new Object[3][100];
    Object[][] eliminar = new Object[3][100];



    public piezas BuscarPiezas(piezas pi) throws FileNotFoundException {
        piezas aux = null;
        int id, stock;
        String descrp;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                id = file.readInt();
                descrp = file.readUTF();
                stock = file.readInt();

                if (pi.getPiz() == id) {
                    aux = new piezas();
                    aux.SetPiz(id);
                    aux.SetDescrp(descrp);
                    aux.SetStock(stock);
                    break;
                }
            }
        } catch (EOFException ex) {
            // Reached end of file
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return aux;
    }

    public void Guardar(piezas pi) throws IOException {
        try {
            file = new RandomAccessFile(path, "rw");
            file.seek(file.length()); // Move to the end to append
            file.writeInt(pi.getPiz());
            file.writeUTF(pi.getDescrp());
            file.writeInt(pi.getStock());
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex; // Propagate the exception
        } finally {
            if (file != null) file.close();
        }
    }

    public void Editar(piezas pi) throws IOException {
        int i = 0, z = 0;
        try {
            file = new RandomAccessFile(path, "rw");
            while (true) {
                datos[0][i] = file.readInt();
                datos[1][i] = file.readUTF();
                datos[2][i] = file.readInt();
                i++;
            }
        } catch (EOFException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex; // Propagate the exception
        } finally {
            if (file != null) file.close();
        }

        for (z = 0; z < i; z++) {
            if (pi.getPiz() == (int) datos[0][z]) {
                datos[0][z] = pi.getPiz();
                datos[1][z] = pi.getDescrp();
                datos[2][z] = pi.getStock();
            }
        }

        try {
            file = new RandomAccessFile(path, "rw");
            file.setLength(0); // Clear the file before rewriting
            for (int j = 0; j < z; j++) {
                file.writeInt((int) datos[0][j]);
                file.writeUTF(datos[1][j].toString());
                file.writeInt((int) datos[2][j]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex; // Propagate the exception
        } finally {
            if (file != null) file.close();
        }
    }


    public int getMax() {
        int maxId = -1, id, stock;
        String descrp;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                id = file.readInt();
                descrp = file.readUTF();
                stock = file.readInt();
                if (id > maxId) maxId = id;
            }
        } catch (EOFException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return maxId + 1;
    }

    public void Eliminar_Piezas(piezas pi) throws IOException {
        int i = 0, z = 0, j = 0;
        try {
            file = new RandomAccessFile(path, "rw");
            while (true) {
                eliminar[0][i] = file.readInt();
                eliminar[1][i] = file.readUTF();
                eliminar[2][i] = file.readInt();
                i++;
            }
        } catch (EOFException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (file != null) file.close();
        }

        for (z = 0; z < i; z++) {
            if ((int) eliminar[0][z] != pi.getPiz()) {
                datos[0][j] = eliminar[0][z];
                datos[1][j] = eliminar[1][z];
                datos[2][j] = eliminar[2][z];
                j++;
            }
        }

        try {
            file = new RandomAccessFile(path, "rw");
            file.setLength(0); // Clear the file before rewriting
            for (int k = 0; k < j; k++) {
                file.writeInt((int) datos[0][k]);
                file.writeUTF(datos[1][k].toString());
                file.writeInt((int) datos[2][k]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (file != null) file.close();
        }
    }
}
