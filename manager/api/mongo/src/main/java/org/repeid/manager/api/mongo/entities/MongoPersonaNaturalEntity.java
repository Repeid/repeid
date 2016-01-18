package org.repeid.manager.api.mongo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Entity
@Table(name = "PERSONA_NATURAL")
@NamedQueries(value = {
		@NamedQuery(name = "MongoPersonaNaturalEntity.findAll", query = "SELECT p FROM MongoPersonaNaturalEntity p"),
		@NamedQuery(name = "MongoPersonaNaturalEntity.findByTipoDocumento", query = "SELECT p FROM MongoPersonaNaturalEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento"),
		@NamedQuery(name = "MongoPersonaNaturalEntity.findByTipoNumeroDocumento", query = "SELECT p FROM MongoPersonaNaturalEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento AND p.numeroDocumento = :numeroDocumento"),
		@NamedQuery(name = "MongoPersonaNaturalEntity.findByFilterText", query = "SELECT p FROM MongoPersonaNaturalEntity p WHERE LOWER(p.numeroDocumento) LIKE :filterText OR LOWER(p.apellidoPaterno) LIKE :filterText OR LOWER(p.apellidoMaterno) LIKE :filterText OR LOWER(p.nombres) LIKE :filterText") })
public class MongoPersonaNaturalEntity extends MongoPersonaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NotNull
	@Size(min = 1, max = 50)
	@NotBlank
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@NotNull
	@Size(min = 1, max = 50)
	@NotBlank
	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@NotNull
	@Size(min = 1, max = 70)
	@NotBlank
	@Column(name = "nombres")
	private String nombres;

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "sexo")
	private String sexo;

	@Size(min = 1, max = 50)
	@Column(name = "estado_civil")
	private String estadoCivil;

	@Size(min = 0, max = 70)
	@Column(name = "ocupacion")
	private String ocupacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stored_file_foto_id", foreignKey = @ForeignKey )
	private MongoStoredFileEntity foto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stored_file_firma_id", foreignKey = @ForeignKey )
	private MongoStoredFileEntity firma;

	public MongoPersonaNaturalEntity() {
		super();
	}

	public MongoPersonaNaturalEntity(String id) {
		super();
		this.id = id;
	}

	public MongoPersonaNaturalEntity(MongoTipoDocumentoEntity tipoDocumento, String numeroDocumento) {
		super(tipoDocumento, numeroDocumento);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public MongoStoredFileEntity getFoto() {
		return foto;
	}

	public void setFoto(MongoStoredFileEntity foto) {
		this.foto = foto;
	}

	public MongoStoredFileEntity getFirma() {
		return firma;
	}

	public void setFirma(MongoStoredFileEntity firma) {
		this.firma = firma;
	}

	@Override
	public String toString() {
		return "(PersonaNaturalEntity id=" + this.id + " tipoDocumento=" + this.tipoDocumento.getAbreviatura()
				+ " numeroDocumento" + this.numeroDocumento + " apellidoPaterno=" + this.apellidoPaterno
				+ " apellidoMaterno=" + this.apellidoMaterno + " nombres=" + this.nombres + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MongoPersonaNaturalEntity))
			return false;
		MongoPersonaNaturalEntity other = (MongoPersonaNaturalEntity) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

}
