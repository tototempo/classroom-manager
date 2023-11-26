package UI;

import Db.DAO.AlumnoDAOImpl;
import Db.DAO.DocenteDAOImpl;
import Db.DAO.PersonaDAO;
import Factories.AlumnoFactory;
import Factories.DocenteFactory;
import Factories.PersonaFactory;
import Main.Persona;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultarPersonal extends JFrame {
    private JRadioButton rbtnDocente;
    private JRadioButton rbtnAlumno;
    private JTextField txtDni;
    private JButton consultarButton;
    private JList jlistPersonal;
    public JPanel panelPersonal;
    private JButton btnConsulTodo;
    ArrayList docente = new ArrayList();
    ArrayList alumno = new ArrayList();
    DefaultListModel modelo = new DefaultListModel();
    PersonaDAO alumnos_db;
    PersonaDAO docentes_db;
    PersonaFactory alumnoFactory;
    PersonaFactory docenteFactory;

    public ConsultarPersonal(){
        setSize(600,600);
        alumnos_db = AlumnoDAOImpl.instance();
        docentes_db = DocenteDAOImpl.instance();
        alumnoFactory = AlumnoFactory.instance();
        docenteFactory = DocenteFactory.instance();

        jlistPersonal.setModel(modelo);

        rbtnDocente.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (rbtnDocente.isSelected()){
                    rbtnAlumno.setSelected(false);
                }
            }
        });
        rbtnAlumno.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (rbtnAlumno.isSelected()){
                    rbtnDocente.setSelected(false);
                }
            }
        });

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.clear();
                if (rbtnAlumno.isSelected()){
                    if (txtDni.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Ingresar DNI sin puntos");
                    }
                    else {
                        try {
                            if(txtDni.getText().length() != 8){
                                JOptionPane.showMessageDialog(null, "El dni debe tener 8 digitos");
                                txtDni.removeAll();
                            } else{
                                Integer dni = Integer.parseInt(txtDni.getText());
                                Persona alumno = alumnoFactory.getFromDb(dni);
                                if(alumno != null){
                                    modelo.addElement(alumno.getNombre() + " " + alumno.getApellido());
                                    JOptionPane.showMessageDialog(null,alumno.toString());
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "No hemos encontrado al " +
                                            "alumno en la base de datos, verifique el dni e intente nuevamente");
                                    txtDni.removeAll();
                                }
                            }
                        }catch (NumberFormatException wrongDniFormat){
                            JOptionPane.showMessageDialog(null, "Porfavor ingrese un numero valido");
                            txtDni.removeAll();
                        }


                    }
                }

                if (rbtnDocente.isSelected()){
                    if (txtDni.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Ingresar DNI sin puntos");
                    }
                    else {
                        try {
                            if(txtDni.getText().length() != 8){
                                JOptionPane.showMessageDialog(null, "El dni debe tener 8 digitos");
                                txtDni.removeAll();
                            } else{
                                Integer dni = Integer.parseInt(txtDni.getText());
                                Persona docente = docenteFactory.getFromDb(dni);
                                if(docente != null){
                                    modelo.addElement(docente.getNombre() + " " + docente.getApellido());
                                    JOptionPane.showMessageDialog(null, docente.toString());
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "No hemos encontrado al " +
                                            "docente en la base de datos, verifique el dni e intente nuevamente");
                                    txtDni.removeAll();
                                }
                            }
                        }catch (NumberFormatException wrongDniFormat){
                            JOptionPane.showMessageDialog(null, "Porfavor ingrese un numero valido");
                            txtDni.removeAll();
                        }


                    }
                }
            }
        });
        btnConsulTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CONSULTAR TODOS
                if (rbtnDocente.isSelected()){
                    //mostrar docentes
                    System.out.println("Hola");
                } else if (rbtnAlumno.isSelected()) {
                    System.out.println("chau");
                }else {
                    JOptionPane.showMessageDialog(null,"Seleccionar un tipo de personal");
                }
            }
        });
    }
}
