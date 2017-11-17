package Controlador;

import Modelo.ClienteDAO;
import Vista.EditarCliente;
import Vista.MenuPrincipal;
import Vista.RegistrarClientes;
import Vista.RegistrarEmpleado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladorCliente implements ActionListener, KeyListener, Comunica {

    RegistrarClientes vista = new RegistrarClientes(this);
    EditarCliente vistaEC = new EditarCliente(this);
    MenuPrincipal vistaAdmin = new MenuPrincipal();
    ClienteDAO modelo = new ClienteDAO();

    public ControladorCliente(RegistrarClientes vista, ClienteDAO modelo) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.botonGuardar.addActionListener(this);
        this.vista.botonRegresar.addActionListener(this);
    }

    public ControladorCliente(EditarCliente vistaEdicion, ClienteDAO modelo,MenuPrincipal menu) {//no sé si esto es así aun lo del menú principal
        this.modelo = modelo;
        this.vistaEC = vistaEdicion;
        this.vistaAdmin = menu; //no sé bien 
        this.vistaEC.botonGuardarEditarCliente.addActionListener(this);
        this.vistaEC.botonRegresarEditarCliente.addActionListener(this);
        this.vistaEC.txtNombreEditarC.addKeyListener(this);
        this.vistaEC.txtApellidoEditarCliente.addKeyListener(this);
        this.vistaEC.txtTelefonoEditarCliente.addKeyListener(this);
        this.vistaEC.txtEmailEditarCliente.addKeyListener(this);
        this.vistaEC.txtDireccionEditarCliente.addKeyListener(this);
        this.vistaEC.txtCPEditarClientes.addKeyListener(this);
        this.vistaEC.txtCiudadEditarClientes.addKeyListener(this);
    }

    public ControladorCliente(MenuPrincipal vistaAdmin, ClienteDAO modelo) {
        this.modelo = modelo;
        this.vistaAdmin = vistaAdmin;
        this.vistaAdmin.botonActualizar.addActionListener(this);
        this.vistaAdmin.editarPopUpClientes.addActionListener(this);
        this.vistaAdmin.eliminarPopUpClientes.addActionListener(this);
        this.vistaAdmin.botonBuscar.addActionListener(this);

    }

    public void inicializar() {

    }

    public void limpiarElementos() {

    }

    public void LlenarTabla(JTable tabla) {
        DefaultTableModel modeloT = new DefaultTableModel();
        tabla.setModel(modeloT);

        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");
        modeloT.addColumn("Teléfono");
        modeloT.addColumn("Email");
        modeloT.addColumn("Dirección");
        modeloT.addColumn("Código Postal");
        modeloT.addColumn("Ciudad");

        Object[] columna = new Object[7];

        int numRegistros = modelo.listCliente().size();
        for (int i = 0; i < numRegistros; i++) {
            columna[0] = modelo.listCliente().get(i).getNombre();
            columna[1] = modelo.listCliente().get(i).getApellido();
            columna[2] = modelo.listCliente().get(i).getTelefono();
            columna[3] = modelo.listCliente().get(i).getEmail();
            columna[4] = modelo.listCliente().get(i).getDireccion();
            columna[5] = modelo.listCliente().get(i).getCodigopostal();
            columna[6] = modelo.listCliente().get(i).getCiudad();
            modeloT.addRow(columna);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.botonGuardar) {
            String nombre = vista.txtNombre.getText();
            String apellido = vista.txtApellido.getText();
            String telefono = vista.txtTelefono.getText();
            String email = vista.txtEmail.getText();
            String direccion = vista.txtDireccion.getText();
            String cp = vista.txtCP.getText();
            String ciudad = vista.txtCiudad.getText();

            String rptaRegistro = modelo.insertCliente(nombre, apellido, telefono, email, direccion, cp, ciudad);

            if (rptaRegistro != null && rptaRegistro.equals("Registro Exitoso")) {
                JOptionPane.showMessageDialog(null, "El registro del cliente " + nombre + " ha sido guardado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al ingresar el registro");
            }
        }
        if (e.getSource() == vistaAdmin.botonActualizar) {
            System.out.println("boton actualizar");
            LlenarTabla(vistaAdmin.tablitaClientes);
            System.out.println("boton actualizar");
        }

        if (e.getSource() == vista.botonRegresar) {
            vista.setVisible(false);
        }
        
        if (e.getSource() == vistaEC.botonRegresarEditarCliente) {
            vistaEC.setVisible(false);
        }

        if (e.getSource() == vistaAdmin.editarPopUpClientes) {
            int filaEditar = vistaAdmin.tablitaClientes.getSelectedRow();
            int numFS = vistaAdmin.tablitaClientes.getSelectedRowCount();
            if (filaEditar >= 0 && numFS == 1) {
                vistaEC.txtNombreEditarC.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 1));
                //vistaEC.txtApellidoEditarCliente.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 1));
                //vistaEC.txtTelefonoEditarCliente.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 2));
                //vistaEC.txtEmailEditarCliente.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 3));
                //vistaEC.txtDireccionEditarCliente.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 4));
                //vistaEC.txtCPEditarClientes.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 5));
                //vistaEC.txtCiudadEditarClientes.setText((String) vistaAdmin.tablitaClientes.getValueAt(filaEditar, 6));

            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila o al menos una");
            }
        }

        if (e.getSource() == vistaEC.botonGuardarEditarCliente) {

            String nombre = vistaEC.txtNombreEditarC.getText();
            String apellido = vistaEC.txtApellidoEditarCliente.getText();
            String telefono = vistaEC.txtTelefonoEditarCliente.getText();
            String email = vistaEC.txtEmailEditarCliente.getText();
            String direccion = vistaEC.txtDireccionEditarCliente.getText();
            String cp = vistaEC.txtCPEditarClientes.getText();
            String ciudad = vistaEC.txtCiudadEditarClientes.getText();

            String rptaRegistro = modelo.editaCliente(nombre, apellido, telefono, email, direccion, cp, ciudad);

            if (rptaRegistro != null && rptaRegistro.equals("Registro Exitoso")) {
                JOptionPane.showMessageDialog(null, "El registro del cliente " + nombre + " ha sido Editado con éxito");
                vistaEC.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al editar el registro");
            }
        }
        if (e.getSource() == vistaAdmin.eliminarPopUpClientes) {
            int filaInicio = vistaAdmin.tablitaClientes.getSelectedRow();
            int numFS = vistaAdmin.tablitaClientes.getSelectedRowCount();
            ArrayList<String> listaAEliminar = new ArrayList();
            String id = "";
            if (filaInicio > 0) {
                for (int i = 0; i < numFS; i++) {
                    id = String.valueOf(vistaAdmin.tablitaClientes.getValueAt(i + filaInicio, 0));
                    listaAEliminar.add(id);
                }
                for (int i = 0; i < numFS; i++) {
                    int rptaUsuario = JOptionPane.showConfirmDialog(null, "¿Quiere eliminar el registro con " + id + "?");
                    if (rptaUsuario == 0) {
                        modelo.eliminarCliente(id);

                    }
                }
                LlenarTabla(vistaAdmin.tablitaClientes);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione al menos una fila a eliminar");
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vistaEC.txtNombreEditarC || e.getSource() == vistaEC.txtApellidoEditarCliente || e.getSource() == vistaEC.txtCiudadEditarClientes) {
            char c = e.getKeyChar();
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c != (char) KeyEvent.VK_SPACE)) {
                e.consume();
            }
        }
        if (e.getSource() == vistaEC.txtTelefonoEditarCliente || e.getSource() == vistaEC.txtNombreEditarC) {
            char c = e.getKeyChar();
            if ((c < '0' || c > '9')) {
                e.consume();
            }

        }
        String Caracteres = vistaEC.txtTelefonoEditarCliente.getText();
        if (Caracteres.length() >= 10) {
            e.consume();

        }
        Caracteres = vistaEC.txtCPEditarClientes.getText();
        if (Caracteres.length() >= 5) {
            e.consume();

        }

        if (e.getSource() == vista.txtNombre || e.getSource() == vista.txtApellido || e.getSource() == vista.txtCiudad) {
            char c = e.getKeyChar();
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c != (char) KeyEvent.VK_SPACE)) {
                e.consume();
            }
        }
        if (e.getSource() == vista.txtTelefono || e.getSource() == vista.txtCP) {
            char c = e.getKeyChar();
            if ((c < '0' || c > '9')) {
                e.consume();
            }

        }
        Caracteres = vista.txtTelefono.getText();
        if (Caracteres.length() >= 10) {
            e.consume();

        }
        Caracteres = vista.txtCP.getText();
        if (Caracteres.length() >= 5) {
            e.consume();

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vistaAdmin.txtBusquedaCliente) {
            String apellidos = vistaAdmin.txtBusquedaCliente.getText();

            DefaultTableModel modeloT = new DefaultTableModel();
            vistaAdmin.tablitaClientes.setModel(modeloT);

            modeloT.addColumn("Nombre");
            modeloT.addColumn("Apellido");
            modeloT.addColumn("Teléfono");
            modeloT.addColumn("Email");
            modeloT.addColumn("Dirección");
            modeloT.addColumn("Código Postal");
            modeloT.addColumn("Ciudad");

            Object[] columna = new Object[7];

            int numRegistros = modelo.buscarClienteApellidos(apellidos).size();

            for (int i = 0; i < numRegistros; i++) {
                columna[0] = modelo.buscarClienteApellidos(apellidos).get(i).getNombre();
                columna[1] = modelo.buscarClienteApellidos(apellidos).get(i).getApellido();
                columna[2] = modelo.buscarClienteApellidos(apellidos).get(i).getTelefono();
                columna[3] = modelo.buscarClienteApellidos(apellidos).get(i).getEmail();
                columna[4] = modelo.buscarClienteApellidos(apellidos).get(i).getDireccion();
                columna[5] = modelo.buscarClienteApellidos(apellidos).get(i).getCodigopostal();
                columna[6] = modelo.buscarClienteApellidos(apellidos).get(i).getCiudad();
                modeloT.addRow(columna);
            }

        }
    }

}
