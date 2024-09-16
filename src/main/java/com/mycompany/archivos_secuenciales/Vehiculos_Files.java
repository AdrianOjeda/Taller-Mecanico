package com.mycompany.archivos_secuenciales;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Vehiculos_Files {

    private DataOutputStream write;
    private DataInputStream read;
    private String path = "C:\\Proyecto\\vehiculos.txt";

    Object[][] datos = new Object[8][100];
    Object[][] eliminar = new Object[8][100];

    public void Guardar(Vehiculos vcs) throws FileNotFoundException {
    try {
        write = new DataOutputStream(new FileOutputStream(path, true));

        write.writeUTF(vcs.getCliente() != null ? vcs.getCliente() : "");
        write.writeInt(vcs.getId_vehiculo());
        write.writeUTF(vcs.getMatricula() != null ? vcs.getMatricula() : "");
        write.writeUTF(vcs.getMarca() != null ? vcs.getMarca() : "");
        write.writeUTF(vcs.getModelo() != null ? vcs.getModelo() : "");
        write.writeUTF(vcs.getFecha() != null ? vcs.getFecha() : "");
        write.writeUTF(vcs.getColor() != null ? vcs.getColor() : "");
        write.writeUTF(vcs.getNota() != null ? vcs.getNota() : "");

    } catch (IOException ex) {
        ex.printStackTrace(); // Mejora el manejo de errores
    } finally {
        if (write != null) {
            try {
                write.close();
            } catch (IOException ex) {
                ex.printStackTrace(); // Manejo de errores al cerrar el flujo
            }
        }
    }
}


    public Vehiculos BuscarIdVehiculo(Vehiculos vcs) throws FileNotFoundException {
        Vehiculos aux = null;
        int id = 0;
        String cliente = "", matricula = "", marca = "", modelo = "", fecha = "", color = "", nota = "" ;
        try {
            read = new DataInputStream(new FileInputStream(path));
            while (true) {

                cliente = read.readUTF();
                id = read.readInt();
                matricula = read.readUTF();
                marca = read.readUTF();
                modelo = read.readUTF();
                fecha = read.readUTF();
                color = read.readUTF();
                nota = read.readUTF();
                if (vcs.getId_vehiculo() == id) {
                    aux = new Vehiculos();

                    aux.setCliente(cliente);
                    aux.setId_vehiculo(id);
                    aux.setMatricula(matricula);
                    aux.setMarca(marca);
                    aux.setModelo(modelo);
                    aux.setFecha(fecha);
                    aux.setColor(color);
                    aux.setNota(nota);
                }
            }
        } catch (IOException ex) {
        }
        try {
            read.close();
        } catch (IOException ex) {

        }
        /*if(aux!=null){
           System.out.print("ID");
           System.out.println(aux.getId_vehiculo()); 
        }*/

        return aux;
    }

    public Vehiculos BuscarMatricula(Vehiculos vcs) throws FileNotFoundException {
        Vehiculos aux = null;
        int id = 0;
        String cliente = "", matricula = "", marca = "", modelo = "", fecha = "", color = "", nota = "";
        try {
            read = new DataInputStream(new FileInputStream(path));
            while (true) {

                cliente = read.readUTF();
                id = read.readInt();
                matricula = read.readUTF();
                marca = read.readUTF();
                modelo = read.readUTF();
                fecha = read.readUTF();
                color = read.readUTF();
                nota = read.readUTF();

                if (vcs.getMatricula().equals(matricula)) {
                    aux = new Vehiculos();

                    aux.setCliente(cliente);
                    aux.setId_vehiculo(id);
                    aux.setMatricula(matricula);
                    aux.setMarca(marca);
                    aux.setModelo(modelo);
                    aux.setFecha(fecha);
                    aux.setColor(color);
                    aux.setNota(nota);
                }
            }
        } catch (IOException ex) {
        }
        try {
            read.close();
        } catch (IOException ex) {

        }

        return aux;
    }

    public void Editar_Vehiculo(Vehiculos vcs) throws IOException {
        int i = 0;
        int z = 0;
        try {
            read = new DataInputStream(new FileInputStream(path));

            while (true) {
                datos[0][i] = read.readUTF();
                datos[1][i] = read.readInt();
                datos[2][i] = read.readUTF();
                datos[3][i] = read.readUTF();
                datos[4][i] = read.readUTF();
                datos[5][i] = read.readUTF();
                datos[6][i] = read.readUTF();
                datos[7][i] = read.readUTF();
                i++;
            }
        } catch (IOException ex) {
        }
        try {
            read.close();

        } catch (IOException ex) {

        }

        while (z < i) {
            if (vcs.getId_vehiculo() == (int) datos[1][z]) {
                System.out.println(datos[0][z]);
                System.out.println(datos[1][z]);
                System.out.println(datos[2][z]);
                System.out.println(datos[5][z]);

                datos[0][z] = vcs.getCliente();
                datos[1][z] = vcs.getId_vehiculo();
                datos[2][z] = vcs.getMatricula();
                datos[3][z] = vcs.getMarca();
                datos[4][z] = vcs.getModelo();
                datos[5][z] = vcs.getFecha();
                datos[6][z] = vcs.getColor();
                datos[7][z] = vcs.getNota();
            }
            z++;

        }

        i = 0;
        try {
            write = new DataOutputStream(new FileOutputStream(path));
            while (i < z) {
                write.writeUTF(datos[0][i].toString());
                write.writeInt((int) datos[1][i]);
                write.writeUTF(datos[2][i].toString());
                write.writeUTF(datos[3][i].toString());
                write.writeUTF(datos[4][i].toString());
                write.writeUTF(datos[5][i].toString());
                write.writeUTF(datos[6][i].toString());
                write.writeUTF(datos[7][i].toString());
                i++;
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException ex) {

                }
            }
        }
    }

    public void Eliminar_Vehiculos(Vehiculos vcs) throws IOException {
        int i = 0;
        int z = 0;
        int id = 0;
        int j;

        datos[0][0] = "";
        datos[1][0] = "";
        datos[2][0] = "";
        datos[3][0] = "";
        datos[4][0] = "";
        datos[5][0] = "";
        datos[6][0] = "";
        datos[7][0] = "";

        try {
            read = new DataInputStream(new FileInputStream(path));

            while (true) {
                eliminar[0][i] = read.readUTF();
                eliminar[1][i] = read.readInt();
                eliminar[2][i] = read.readUTF();
                eliminar[3][i] = read.readUTF();
                eliminar[4][i] = read.readUTF();
                eliminar[5][i] = read.readUTF();
                eliminar[6][i] = read.readUTF();
                eliminar[7][i] = read.readUTF();

                System.out.println("ELIMINAR");
                System.out.println(eliminar[0][i]);
                System.out.println(eliminar[1][i]);
                System.out.println(eliminar[2][i]);
                System.out.println(eliminar[5][i]);

                i++;
            }
        } catch (IOException ex) {
        }
        try {
            read.close();

        } catch (IOException ex) {

        }

        j = 0;
        while (z < i) {
            if ((int) eliminar[1][z] != vcs.getId_vehiculo()) {
                datos[0][j] = eliminar[0][z];
                datos[1][j] = eliminar[1][z];
                datos[2][j] = eliminar[2][z];
                datos[3][j] = eliminar[3][z];
                datos[4][j] = eliminar[4][z];
                datos[5][j] = eliminar[5][z];
                datos[6][j] = eliminar[6][z];
                datos[7][j] = eliminar[7][z];

                System.out.println("ELIMINAR SEGUNDA ETAPA");
                System.out.println(eliminar[0][z]);
                System.out.println(eliminar[1][z]);
                System.out.println(eliminar[2][z]);
                System.out.println(eliminar[5][z]);

                j++;
            }
            z++;
        }
        i = 0;

        try {
            write = new DataOutputStream(new FileOutputStream(path));
            while (i < j) {
                write.writeUTF(datos[0][i].toString());
                write.writeInt((int) datos[1][i]);
                write.writeUTF(datos[2][i].toString());
                write.writeUTF(datos[3][i].toString());
                write.writeUTF(datos[4][i].toString());
                write.writeUTF(datos[5][i].toString());
                write.writeUTF(datos[6][i].toString());
                write.writeUTF(datos[7][i].toString());
                i++;
            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException ex) {

                }
            }
        }
    }

    public int getMax() {
        int maxId = -1; // Inicializar con un valor negativo para manejar el caso de archivo vacío
        int id = 0;
        String cliente = "", matricula = "", marca = "", modelo = "", fecha = "", color = "", nota = "";
        try {
            read = new DataInputStream(new FileInputStream(path));

            while (true) {

                cliente = read.readUTF();
                id = read.readInt();
                matricula = read.readUTF();
                marca = read.readUTF();
                modelo = read.readUTF();
                fecha = read.readUTF();
                color = read.readUTF();
                nota = read.readUTF();

                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (EOFException e) {
            // Fin del archivo, no se hace nada aquí
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes");
        } finally {
            try {
                if (read != null) {
                    read.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo de lectura");
            }
        }

        // Verificar si hay IDs sin asignar desde 0 al máximo encontrado
        for (int i = 0; i <= maxId; i++) {
            boolean idEncontrado = false;

            try {
                read = new DataInputStream(new FileInputStream(path));

                while (true) {
                    cliente = read.readUTF();
                    id = read.readInt();
                    matricula = read.readUTF();
                    marca = read.readUTF();
                    modelo = read.readUTF();
                    fecha = read.readUTF();
                    color = read.readUTF();
                    nota = read.readUTF();
                    if (id == i) {
                        idEncontrado = true;
                        break;
                    }
                }
            } catch (EOFException e) {
                // Fin del archivo, no se hace nada aquí
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de clientes");
            } finally {
                try {
                    if (read != null) {
                        read.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error al cerrar el archivo de lectura");
                }
            }

            if (!idEncontrado) {
                return i; // Devolver el primer ID sin asignar encontrado
            }
        }
        System.out.println("MAXID");
        System.out.println(maxId);
        return maxId + 1; // Todos los IDs están asignados, asignar el siguiente número
    }

}
