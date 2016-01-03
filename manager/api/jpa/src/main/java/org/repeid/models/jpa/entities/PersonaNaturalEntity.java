package org.repeid.models.jpa.entities;

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
        @NamedQuery(name = "PersonaNaturalEntity.findAll", query = "SELECT p FROM PersonaNaturalEntity p"),
        @NamedQuery(name = "PersonaNaturalEntity.findByTipoDocumento", query = "SELECT p FROM PersonaNaturalEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento"),
        @NamedQuery(name = "PersonaNaturalEntity.findByTipoNumeroDocumento", query = "SELECT p FROM PersonaNaturalEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento AND p.numeroDocumento = :numeroDocumento"),
        @NamedQuery(name = "PersonaNaturalEntity.findByFilterText", query = "SELECT p FROM PersonaNaturalEntity p WHERE LOWER(p.numeroDocumento) LIKE :filterText OR LOWER(p.apellidoPaterno) LIKE :filterText OR LOWER(p.apellidoMaterno) LIKE :filterText OR LOWER(p.nombres) LIKE :filterText") })
public class PersonaNaturalEntity extends PersonaEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @NotBlank
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;

    @NotNull
    @Size(min = 1, max = 50)
    @NotBlank
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @NotNull
    @Size(min = 1, max = 70)
    @NotBlank
    @Column(name = "NOMBRES")
    private String nombres;

    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SEXO")
    private String sexo;

    @Size(min = 1, max = 50)
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;

    @Size(min = 0, max = 70)
    @Column(name = "OCUPACION")
    private String ocupacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORED_FILE_FOTO_ID", foreignKey = @ForeignKey )
    private StoredFileEntity foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORED_FILE_FIRMA_ID", foreignKey = @ForeignKey )
    private StoredFileEntity firma;

    public PersonaNaturalEntity() {
        super();
    }

    public PersonaNaturalEntity(String id) {
        super();
        this.id = id;
    }

    public PersonaNaturalEntity(TipoDocumentoEntity tipoDocumento, String numeroDocumento) {
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

    public StoredFileEntity getFoto() {
        return foto;
    }

    public void setFoto(StoredFileEntity foto) {
        this.foto = foto;
    }

    public StoredFileEntity getFirma() {
        return firma;
    }

    public void setFirma(StoredFileEntity firma) {
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
        if (!(obj instanceof PersonaNaturalEntity))
            return false;
        PersonaNaturalEntity other = (PersonaNaturalEntity) obj;
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
