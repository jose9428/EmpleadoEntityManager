package com.edu.pe.dao;

import java.util.List;

import com.edu.pe.models.Empleado;

public interface IEmpleadoDao {

	public void Guardar(Empleado e);
	
	public void Actualizar(Empleado e);
	
	public void Eliminar(int id);
	
	public List<Empleado> Listado();
	
	public Empleado BuscarPorId(int id);
	
	public List<Empleado> findAll();
	
	public List<Empleado> ListProc(int sueldo);
}
