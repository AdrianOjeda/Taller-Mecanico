package com.mycompany.archivos_secuenciales;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Files {
    private RandomAccessFile file;
    private String path = "C:\\Proyecto\\archivo.txt";


    Object[][] datos = new Object[9][100];
    Object[][] eliminar = new Object[9][100];

    public int Id() throws FileNotFoundException {
        int Idmax = 0;
        List<Integer> assignedIds = new ArrayList<>();
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                int id = file.readInt();
                file.readUTF();
                file.readUTF();
                file.readUTF();
                file.readUTF();
                file.readUTF();
                file.readUTF();
                file.readUTF();
                file.readUTF();
                assignedIds.add(id);

                if (id > Idmax) {
                    Idmax = id;
                }
            }
        } catch (EOFException ex) {
            // End of file reached
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        for (int i = 0; i < Idmax; i++) {
            if (!assignedIds.contains(i)) {
                return i;
            }
        }
        return Idmax + 1;
    }

    public void Guardar(contacto cto) throws FileNotFoundException {
        try {
            file = new RandomAccessFile(path, "rw");
            file.seek(file.length());  // Move to the end of the file for appending
            file.writeInt(cto.getId());
            file.writeUTF(cto.getNombre());
            file.writeUTF(cto.getTelefono());
            file.writeUTF(cto.getPaterno());
            file.writeUTF(cto.getMaterno());
            file.writeUTF(cto.getUsername());
            file.writeUTF(cto.getPerfil());
            file.writeUTF(cto.getDireccion());
            file.writeUTF(cto.getPassword());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public contacto BuscarContacto(contacto cto) throws FileNotFoundException {
        contacto aux = null;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                int id = file.readInt();
                String nombre = file.readUTF();
                String telefono = file.readUTF();
                String paterno = file.readUTF();
                String materno = file.readUTF();
                String username = file.readUTF();
                String perfil = file.readUTF();
                String direccion = file.readUTF();
                String password = file.readUTF();
                if (cto.getId() == id) {
                    aux = new contacto();
                    aux.setId(id);
                    aux.setNombre(nombre);
                    aux.setTelefono(telefono);
                    aux.setPaterno(paterno);
                    aux.setMaterno(materno);
                    aux.setUsername(username);
                    aux.setPerfil(perfil);
                    aux.setDireccion(direccion);
                    aux.setPassword(password);
                    break;  // Contact found, exit loop
                }
            }
        } catch (EOFException ex) {
            // End of file reached
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return aux;
    }

    public contacto BuscarUsuario(contacto cto) throws FileNotFoundException {
        contacto aux = null;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                int id = file.readInt();
                String nombre = file.readUTF();
                String telefono = file.readUTF();
                String paterno = file.readUTF();
                String materno = file.readUTF();
                String username = file.readUTF();
                String perfil = file.readUTF();
                String direccion = file.readUTF();
                String password = file.readUTF();
                if (cto.getUsername().equals(username)) {
                    aux = new contacto();
                    aux.setId(id);
                    aux.setNombre(nombre);
                    aux.setTelefono(telefono);
                    aux.setPaterno(paterno);
                    aux.setMaterno(materno);
                    aux.setUsername(username);
                    aux.setPerfil(perfil);
                    aux.setDireccion(direccion);
                    aux.setPassword(password);
                    break;  // User found, exit loop
                }
            }
        } catch (EOFException ex) {
            // End of file reached
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return aux;
    }

    public void Editar(contacto cto) throws IOException {
        int i = 0;
        int z = 0;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                datos[0][i] = file.readInt();
                datos[1][i] = file.readUTF();
                datos[2][i] = file.readUTF();
                datos[3][i] = file.readUTF();
                datos[4][i] = file.readUTF();
                datos[5][i] = file.readUTF();
                datos[6][i] = file.readUTF();
                datos[7][i] = file.readUTF();
                datos[8][i] = file.readUTF();
                i++;
            }
        } catch (EOFException ex) {
            // End of file reached
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        while (z < i) {
            if (cto.getId() == (int) datos[0][z]) {
                datos[0][z] = cto.getId();
                datos[1][z] = cto.getNombre();
                datos[2][z] = cto.getTelefono();
                datos[3][z] = cto.getPaterno();
                datos[4][z] = cto.getMaterno();
                datos[5][z] = cto.getUsername();
                datos[6][z] = cto.getPerfil();
                datos[7][z] = cto.getDireccion();
                datos[8][z] = cto.getPassword();
            }
            z++;
        }

        i = 0;
        try {
            file = new RandomAccessFile(path, "rw");
            file.setLength(0);  // Clear file content
            while (i < z) {
                file.writeInt((int) datos[0][i]);
                file.writeUTF(datos[1][i].toString());
                file.writeUTF(datos[2][i].toString());
                file.writeUTF(datos[3][i].toString());
                file.writeUTF(datos[4][i].toString());
                file.writeUTF(datos[5][i].toString());
                file.writeUTF(datos[6][i].toString());
                file.writeUTF(datos[7][i].toString());
                file.writeUTF(datos[8][i].toString());
                i++;
            }
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void Eliminar(contacto cto) throws IOException {
        int i = 0;
        int z = 0;
        int j;
        eliminar[0][0] = "";
        eliminar[1][0] = "";
        eliminar[2][0] = "";
        eliminar[3][0] = "";
        eliminar[4][0] = "";
        eliminar[5][0] = "";
        eliminar[6][0] = "";
        eliminar[7][0] = "";
        eliminar[8][0] = "";

        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                eliminar[0][i] = file.readInt();
                eliminar[1][i] = file.readUTF();
                eliminar[2][i] = file.readUTF();
                eliminar[3][i] = file.readUTF();
                eliminar[4][i] = file.readUTF();
                eliminar[5][i] = file.readUTF();
                eliminar[6][i] = file.readUTF();
                eliminar[7][i] = file.readUTF();
                eliminar[8][i] = file.readUTF();
                i++;
            }
        } catch (EOFException ex) {
            // End of file reached
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        j = 0;
        while (z < i) {
            if ((int) eliminar[0][z] != cto.getId()) {
                datos[0][j] = eliminar[0][z];
                datos[1][j] = eliminar[1][z];
                datos[2][j] = eliminar[2][z];
                datos[3][j] = eliminar[3][z];
                datos[4][j] = eliminar[4][z];
                datos[5][j] = eliminar[5][z];
                datos[6][j] = eliminar[6][z];
                datos[7][j] = eliminar[7][z];
                datos[8][j] = eliminar[8][z];
                j++;
            }
            z++;
        }

        i = 0;
        try {
            file = new RandomAccessFile(path, "rw");
            file.setLength(0);  // Clear file content
            while (i < j) {
                file.writeInt((int) datos[0][i]);
                file.writeUTF(datos[1][i].toString());
                file.writeUTF(datos[2][i].toString());
                file.writeUTF(datos[3][i].toString());
                file.writeUTF(datos[4][i].toString());
                file.writeUTF(datos[5][i].toString());
                file.writeUTF(datos[6][i].toString());
                file.writeUTF(datos[7][i].toString());
                file.writeUTF(datos[8][i].toString());
                i++;
            }
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
