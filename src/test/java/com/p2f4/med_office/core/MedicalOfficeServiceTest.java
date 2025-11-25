package com.p2f4.med_office.core;

import com.p2f4.med_office.domain.ClinicRepository;
import com.p2f4.med_office.domain.MedicalOfficeRepository;
import com.p2f4.med_office.domain.SpecialtyRepository;
import com.p2f4.med_office.dto.MedicalOfficeDTO;
import com.p2f4.med_office.dto.MedicalOfficeDataUpdatableDTO;
import com.p2f4.med_office.dto.MedicalOfficeParamsDTO;
import com.p2f4.med_office.dto.ScheduleDTO;
import com.p2f4.med_office.entity.Clinic;
import com.p2f4.med_office.entity.MedicalOffice;
import com.p2f4.med_office.entity.Specialty;
import com.p2f4.med_office.mapper.MedicalOfficeMapper;
import com.p2f4.med_office.utils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para MedicalOfficeService
 * Implementa el patrón AAA (Arrange, Act, Assert)
 * 
 * Cobertura de casos:
 * - Casos felices (happy path)
 * - Casos de error y excepciones controladas
 * - Validaciones de lógica de negocio
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Medical Office Service Tests")
class MedicalOfficeServiceTest {

    @Mock
    private MedicalOfficeRepository medicalOfficeRepository;

    @Mock
    private ClinicRepository clinicRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private MedicalOfficeMapper medicalOfficeMapper;

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private MedicalOfficeService medicalOfficeService;

    // Variables de prueba comunes
    private Clinic activeClinic;
    private Clinic inactiveClinic;
    private Specialty specialty;
    private MedicalOffice medicalOffice;
    private MedicalOfficeDTO medicalOfficeDTO;

    @BeforeEach
    void setUp() {
        // Arrange - Configuración común para todas las pruebas
        activeClinic = new Clinic();
        activeClinic.setIdClinic(1);
        activeClinic.setName("Clinica Central");
        activeClinic.setStatus(EnumStatus.ACTIVO);

        inactiveClinic = new Clinic();
        inactiveClinic.setIdClinic(2);
        inactiveClinic.setName("Clinica Inactiva");
        inactiveClinic.setStatus(EnumStatus.INACTIVO);

        specialty = new Specialty();
        specialty.setIdSpecialty(1);
        specialty.setSpecialtyName("Cardiologia");

        medicalOffice = new MedicalOffice();
        medicalOffice.setIdOffice(1);
        medicalOffice.setOfficeNumber(101);
        medicalOffice.setIdClinic(1);
        medicalOffice.setIdSpecialty(1);
        medicalOffice.setStatus(EnumStatus.ACTIVO);

        medicalOfficeDTO = new MedicalOfficeDTO();
        medicalOfficeDTO.setIdOffice(1);
        medicalOfficeDTO.setOfficeNumber(101);
        medicalOfficeDTO.setIdClinic(1);
        medicalOfficeDTO.setIdSpecialty(1);
        medicalOfficeDTO.setStatus("ACTIVE");
    }

    @Nested
    @DisplayName("Tests para createMedicalOffice con IDs")
    class CreateMedicalOfficeWithIdsTests {

        @Test
        @DisplayName("Caso Feliz: Crear consultorio médico exitosamente con IDs")
        void createMedicalOffice_WithValidIds_ShouldReturnCreatedOffice() {
            // ARRANGE
            Integer officeNumber = 101;
            Integer idClinic = 1;
            Integer idSpecialty = 1;
            String status = "ACTIVE";

            when(clinicRepository.findById(idClinic)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findById(idSpecialty)).thenReturn(Optional.of(specialty));
            when(medicalOfficeRepository.existsByIdClinicAndOfficeNumber(idClinic, officeNumber))
                    .thenReturn(false);
            when(medicalOfficeMapper.toEntity(any(MedicalOfficeDTO.class))).thenReturn(medicalOffice);
            when(medicalOfficeRepository.save(any(MedicalOffice.class))).thenReturn(medicalOffice);
            when(medicalOfficeMapper.toDTO(any(MedicalOffice.class))).thenReturn(medicalOfficeDTO);

            // ACT
            MedicalOfficeDTO result = medicalOfficeService.createMedicalOffice(
                    officeNumber, idClinic, idSpecialty, status);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertEquals(officeNumber, result.getOfficeNumber(), "El número de consultorio debe coincidir");
            assertEquals(idClinic, result.getIdClinic(), "El ID de clínica debe coincidir");
            assertEquals(idSpecialty, result.getIdSpecialty(), "El ID de especialidad debe coincidir");
            assertEquals(status, result.getStatus(), "El estado debe coincidir");

            // Verificar interacciones
            verify(clinicRepository, times(1)).findById(idClinic);
            verify(specialtyRepository, times(1)).findById(idSpecialty);
            verify(medicalOfficeRepository, times(1))
                    .existsByIdClinicAndOfficeNumber(idClinic, officeNumber);
            verify(medicalOfficeRepository, times(1)).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando la clínica no existe")
        void createMedicalOffice_WithNonExistentClinic_ShouldThrowClinicNotFoundException() {
            // ARRANGE
            Integer officeNumber = 101;
            Integer idClinic = 999; // ID inexistente
            Integer idSpecialty = 1;
            String status = "ACTIVE";

            when(clinicRepository.findById(idClinic)).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(ClinicNotFoundException.class, () -> {
                medicalOfficeService.createMedicalOffice(officeNumber, idClinic, idSpecialty, status);
            }, "Debe lanzar ClinicNotFoundException cuando la clínica no existe");

            // Verificar que no se intentó guardar
            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando la especialidad no existe")
        void createMedicalOffice_WithNonExistentSpecialty_ShouldThrowSpecialtyNotFoundException() {
            // ARRANGE
            Integer officeNumber = 101;
            Integer idClinic = 1;
            Integer idSpecialty = 999; // ID inexistente
            String status = "ACTIVE";

            when(clinicRepository.findById(idClinic)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findById(idSpecialty)).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(SpecialtyNotFoundException.class, () -> {
                medicalOfficeService.createMedicalOffice(officeNumber, idClinic, idSpecialty, status);
            }, "Debe lanzar SpecialtyNotFoundException cuando la especialidad no existe");

            // Verificar que no se intentó guardar
            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando la clínica está inactiva")
        void createMedicalOffice_WithInactiveClinic_ShouldThrowClinicInactiveException() {
            // ARRANGE
            Integer officeNumber = 101;
            Integer idClinic = 2; // Clínica inactiva
            Integer idSpecialty = 1;
            String status = "ACTIVE";

            when(clinicRepository.findById(idClinic)).thenReturn(Optional.of(inactiveClinic));
            when(specialtyRepository.findById(idSpecialty)).thenReturn(Optional.of(specialty));

            // ACT & ASSERT
            assertThrows(ClinicInactiveException.class, () -> {
                medicalOfficeService.createMedicalOffice(officeNumber, idClinic, idSpecialty, status);
            }, "Debe lanzar ClinicInactiveException cuando la clínica está inactiva");

            // Verificar que no se intentó guardar
            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando el número de consultorio ya existe")
        void createMedicalOffice_WithDuplicateOfficeNumber_ShouldThrowOfficeNumberDuplicateException() {
            // ARRANGE
            Integer officeNumber = 101;
            Integer idClinic = 1;
            Integer idSpecialty = 1;
            String status = "ACTIVE";

            when(clinicRepository.findById(idClinic)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findById(idSpecialty)).thenReturn(Optional.of(specialty));
            when(medicalOfficeRepository.existsByIdClinicAndOfficeNumber(idClinic, officeNumber))
                    .thenReturn(true); // Ya existe

            // ACT & ASSERT
            assertThrows(OfficeNumberDuplicateException.class, () -> {
                medicalOfficeService.createMedicalOffice(officeNumber, idClinic, idSpecialty, status);
            }, "Debe lanzar OfficeNumberDuplicateException cuando el número ya existe");

            // Verificar que no se intentó guardar
            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }
    }

    @Nested
    @DisplayName("Tests para createMedicalOffice con nombres")
    class CreateMedicalOfficeWithNamesTests {

        @Test
        @DisplayName("Caso Feliz: Crear consultorio médico exitosamente con nombres")
        void createMedicalOffice_WithValidNames_ShouldReturnCreatedOffice() {
            // ARRANGE
            Integer officeNumber = 102;
            String clinicName = "Clinica Central";
            String specialtyName = "Cardiologia";
            String status = "ACTIVE";

            // Crear un DTO con el número de consultorio correcto
            MedicalOfficeDTO expectedDTO = new MedicalOfficeDTO();
            expectedDTO.setIdOffice(2);
            expectedDTO.setOfficeNumber(officeNumber); // 102
            expectedDTO.setIdClinic(1);
            expectedDTO.setIdSpecialty(1);
            expectedDTO.setStatus("ACTIVE");

            when(clinicRepository.findByName(clinicName)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findBySpecialtyName(specialtyName)).thenReturn(Optional.of(specialty));
            when(medicalOfficeRepository.existsByIdClinicAndOfficeNumber(activeClinic.getIdClinic(), officeNumber))
                    .thenReturn(false);
            when(medicalOfficeRepository.save(any(MedicalOffice.class))).thenReturn(medicalOffice);
            when(medicalOfficeMapper.toDTO(any(MedicalOffice.class))).thenReturn(expectedDTO);

            // ACT
            MedicalOfficeDTO result = medicalOfficeService.createMedicalOffice(
                    officeNumber, clinicName, specialtyName, status);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertEquals(officeNumber, result.getOfficeNumber(), "El número de consultorio debe coincidir");

            // Verificar interacciones
            verify(clinicRepository, times(1)).findByName(clinicName);
            verify(specialtyRepository, times(1)).findBySpecialtyName(specialtyName);
            verify(medicalOfficeRepository, times(1)).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando el nombre de clínica no existe")
        void createMedicalOffice_WithNonExistentClinicName_ShouldThrowClinicNotFoundException() {
            // ARRANGE
            Integer officeNumber = 102;
            String clinicName = "Clinica Inexistente";
            String specialtyName = "Cardiologia";
            String status = "ACTIVE";

            when(clinicRepository.findByName(clinicName)).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(ClinicNotFoundException.class, () -> {
                medicalOfficeService.createMedicalOffice(officeNumber, clinicName, specialtyName, status);
            }, "Debe lanzar ClinicNotFoundException cuando el nombre de clínica no existe");

            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando el nombre de especialidad no existe")
        void createMedicalOffice_WithNonExistentSpecialtyName_ShouldThrowSpecialtyNotFoundException() {
            // ARRANGE
            Integer officeNumber = 102;
            String clinicName = "Clinica Central";
            String specialtyName = "Especialidad Inexistente";
            String status = "ACTIVE";

            when(clinicRepository.findByName(clinicName)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findBySpecialtyName(specialtyName)).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(SpecialtyNotFoundException.class, () -> {
                medicalOfficeService.createMedicalOffice(officeNumber, clinicName, specialtyName, status);
            }, "Debe lanzar SpecialtyNotFoundException cuando el nombre de especialidad no existe");

            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }
    }

    @Nested
    @DisplayName("Tests para updateMedicalOffice")
    class UpdateMedicalOfficeTests {

        @Test
        @DisplayName("Caso Feliz: Actualizar consultorio médico exitosamente")
        void updateMedicalOffice_WithValidData_ShouldReturnUpdatedOffice() {
            // ARRANGE
            Integer idUser = 1;
            Integer idMedicalOffice = 1;
            Integer newOfficeNumber = 102;
            String clinicName = "Clinica Central";
            String specialtyName = "Cardiologia";
            String status = "ACTIVE";
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7);

            when(medicalOfficeRepository.findById(idMedicalOffice)).thenReturn(Optional.of(medicalOffice));
            when(clinicRepository.findByNameIgnoreCase(clinicName)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findBySpecialtyNameIgnoreCase(specialtyName))
                    .thenReturn(Optional.of(specialty));
            when(medicalOfficeRepository.existsByIdClinicAndOfficeNumber(
                    activeClinic.getIdClinic(), newOfficeNumber)).thenReturn(false);
            when(medicalOfficeRepository.save(any(MedicalOffice.class))).thenReturn(medicalOffice);
            when(medicalOfficeMapper.toDTO(any(MedicalOffice.class))).thenReturn(medicalOfficeDTO);

            // ACT
            MedicalOfficeDTO result = medicalOfficeService.updateMedicalOffice(
                    idUser, idMedicalOffice, newOfficeNumber, clinicName, specialtyName, status, startDate, endDate);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            verify(medicalOfficeRepository, times(1)).findById(idMedicalOffice);
            verify(medicalOfficeRepository, times(1)).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Feliz: Actualizar a estado MANTENIMIENTO crea schedule")
        void updateMedicalOffice_WithMaintenanceStatus_ShouldCreateMaintenanceSchedule() {
            // ARRANGE
            Integer idUser = 1;
            Integer idMedicalOffice = 1;
            Integer officeNumber = 101;
            String clinicName = "Clinica Central";
            String specialtyName = "Cardiologia";
            String status = "MANTENIMIENTO";
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7);

            when(medicalOfficeRepository.findById(idMedicalOffice)).thenReturn(Optional.of(medicalOffice));
            when(clinicRepository.findByNameIgnoreCase(clinicName)).thenReturn(Optional.of(activeClinic));
            when(specialtyRepository.findBySpecialtyNameIgnoreCase(specialtyName))
                    .thenReturn(Optional.of(specialty));
            when(medicalOfficeRepository.save(any(MedicalOffice.class))).thenReturn(medicalOffice);
            when(medicalOfficeMapper.toDTO(any(MedicalOffice.class))).thenReturn(medicalOfficeDTO);

            // ACT
            MedicalOfficeDTO result = medicalOfficeService.updateMedicalOffice(
                    idUser, idMedicalOffice, officeNumber, clinicName, specialtyName, status, startDate, endDate);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            verify(scheduleService, times(1)).createMaintenanceSchedule(
                    eq(idUser),  eq(medicalOffice.getIdOffice()), eq(startDate), eq(endDate));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando el consultorio no existe")
        void updateMedicalOffice_WithNonExistentOffice_ShouldThrowMedicalOfficeNotFoundException() {
            // ARRANGE
            Integer idUser = 1;
            Integer idMedicalOffice = 999; // No existe
            Integer officeNumber = 101;
            String clinicName = "Clinica Central";
            String specialtyName = "Cardiologia";
            String status = "ACTIVE";
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7);

            when(medicalOfficeRepository.findById(idMedicalOffice)).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(MedicalOfficeNotFoundException.class, () -> {
                medicalOfficeService.updateMedicalOffice(
                        idUser, idMedicalOffice, officeNumber, clinicName, specialtyName, status, startDate, endDate);
            }, "Debe lanzar MedicalOfficeNotFoundException cuando el consultorio no existe");

            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando se intenta actualizar con clínica inactiva")
        void updateMedicalOffice_WithInactiveClinic_ShouldThrowClinicInactiveException() {
            // ARRANGE
            Integer idUser = 1;
            Integer idMedicalOffice = 1;
            Integer officeNumber = 101;
            String clinicName = "Clinica Inactiva";
            String specialtyName = "Cardiologia";
            String status = "ACTIVE";
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7);

            when(medicalOfficeRepository.findById(idMedicalOffice)).thenReturn(Optional.of(medicalOffice));
            when(clinicRepository.findByNameIgnoreCase(clinicName)).thenReturn(Optional.of(inactiveClinic));
            when(specialtyRepository.findBySpecialtyNameIgnoreCase(specialtyName))
                    .thenReturn(Optional.of(specialty));

            // ACT & ASSERT
            assertThrows(ClinicInactiveException.class, () -> {
                medicalOfficeService.updateMedicalOffice(
                        idUser, idMedicalOffice, officeNumber, clinicName, specialtyName, status, startDate, endDate);
            }, "Debe lanzar ClinicInactiveException cuando la clínica está inactiva");

            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }
    }

    @Nested
    @DisplayName("Tests para deactivateMedicalOffice")
    class DeactivateMedicalOfficeTests {

        @Test
        @DisplayName("Caso Feliz: Desactivar consultorio médico exitosamente")
        void deactivateMedicalOffice_WithValidId_ShouldReturnDeactivatedOffice() {
            // ARRANGE
            Integer idMedicalOffice = 1;
            MedicalOffice activeOffice = new MedicalOffice();
            activeOffice.setIdOffice(idMedicalOffice);
            activeOffice.setStatus(EnumStatus.ACTIVO);

            when(medicalOfficeRepository.findById(idMedicalOffice)).thenReturn(Optional.of(activeOffice));
            when(medicalOfficeRepository.save(any(MedicalOffice.class))).thenAnswer(invocation -> {
                MedicalOffice saved = invocation.getArgument(0);
                return saved;
            });

            // ACT
            MedicalOffice result = medicalOfficeService.deactivateMedicalOffice(idMedicalOffice);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertEquals("INACTIVE", result.getStatus(), "El estado debe ser INACTIVE");
            verify(medicalOfficeRepository, times(1)).findById(idMedicalOffice);
            verify(medicalOfficeRepository, times(1)).save(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Error: Falla cuando el consultorio no existe")
        void deactivateMedicalOffice_WithNonExistentId_ShouldThrowMedicalOfficeNotFoundException() {
            // ARRANGE
            Integer idMedicalOffice = 999; // No existe

            when(medicalOfficeRepository.findById(idMedicalOffice)).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(MedicalOfficeNotFoundException.class, () -> {
                medicalOfficeService.deactivateMedicalOffice(idMedicalOffice);
            }, "Debe lanzar MedicalOfficeNotFoundException cuando el consultorio no existe");

            verify(medicalOfficeRepository, never()).save(any(MedicalOffice.class));
        }
    }

    @Nested
    @DisplayName("Tests para getAllMedicalOffices")
    class GetAllMedicalOfficesTests {

        @Test
        @DisplayName("Caso Feliz: Obtener todos los consultorios médicos")
        void getAllMedicalOffices_ShouldReturnListOfOffices() {
            // ARRANGE
            MedicalOffice office1 = new MedicalOffice();
            office1.setIdOffice(1);
            office1.setOfficeNumber(101);

            MedicalOffice office2 = new MedicalOffice();
            office2.setIdOffice(2);
            office2.setOfficeNumber(102);

            List<MedicalOffice> offices = Arrays.asList(office1, office2);

            MedicalOfficeParamsDTO dto1 = new MedicalOfficeParamsDTO();
            dto1.setIdOffice(1);
            dto1.setOfficeNumber(101);

            MedicalOfficeParamsDTO dto2 = new MedicalOfficeParamsDTO();
            dto2.setIdOffice(2);
            dto2.setOfficeNumber(102);

            when(medicalOfficeRepository.findAll()).thenReturn(offices);
            when(medicalOfficeMapper.toParamsDTO(office1)).thenReturn(dto1);
            when(medicalOfficeMapper.toParamsDTO(office2)).thenReturn(dto2);

            // ACT
            List<MedicalOfficeParamsDTO> result = medicalOfficeService.getAllMedicalOffices();

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertEquals(2, result.size(), "Debe retornar 2 consultorios");
            assertEquals(101, result.get(0).getOfficeNumber(), "El primer consultorio debe ser 101");
            assertEquals(102, result.get(1).getOfficeNumber(), "El segundo consultorio debe ser 102");

            verify(medicalOfficeRepository, times(1)).findAll();
            verify(medicalOfficeMapper, times(2)).toParamsDTO(any(MedicalOffice.class));
        }

        @Test
        @DisplayName("Caso Feliz: Retornar lista vacía cuando no hay consultorios")
        void getAllMedicalOffices_WithNoOffices_ShouldReturnEmptyList() {
            // ARRANGE
            when(medicalOfficeRepository.findAll()).thenReturn(Arrays.asList());

            // ACT
            List<MedicalOfficeParamsDTO> result = medicalOfficeService.getAllMedicalOffices();

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertTrue(result.isEmpty(), "La lista debe estar vacía");

            verify(medicalOfficeRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para mergeMedicalOfficesWithSchedules")
    class MergeMedicalOfficesWithSchedulesTests {

        @Test
        @DisplayName("Caso Feliz: Combinar consultorios con schedules correctamente")
        void mergeMedicalOfficesWithSchedules_ShouldMergeCorrectly() {
            // ARRANGE
            MedicalOfficeParamsDTO office1 = new MedicalOfficeParamsDTO();
            office1.setIdOffice(1);
            office1.setOfficeNumber(101);
            office1.setClinicName("Clinica Central");
            office1.setSpecialtyName("Cardiologia");
            office1.setStatus("ACTIVE");

            MedicalOfficeParamsDTO office2 = new MedicalOfficeParamsDTO();
            office2.setIdOffice(2);
            office2.setOfficeNumber(102);
            office2.setClinicName("Clinica Norte");
            office2.setSpecialtyName("Neurologia");
            office2.setStatus("ACTIVE");

            List<MedicalOfficeParamsDTO> offices = Arrays.asList(office1, office2);

            ScheduleDTO schedule1 = new ScheduleDTO();
            schedule1.setIdOffice(1);
            schedule1.setStartDate(LocalDate.now());
            schedule1.setEndDate(LocalDate.now().plusDays(7));

            List<ScheduleDTO> schedules = Arrays.asList(schedule1);

            // ACT
            List<MedicalOfficeDataUpdatableDTO> result = 
                    medicalOfficeService.mergeMedicalOfficesWithSchedules(offices, schedules);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertEquals(2, result.size(), "Debe retornar 2 consultorios");
            
            // Verificar el consultorio con schedule
            MedicalOfficeDataUpdatableDTO mergedOffice1 = result.get(0);
            assertEquals(101, mergedOffice1.getOfficeNumber(), "El número debe coincidir");
            assertNotNull(mergedOffice1.getStartDate(), "La fecha de inicio no debe ser nula");
            assertNotNull(mergedOffice1.getEndDate(), "La fecha fin no debe ser nula");

            // Verificar el consultorio sin schedule
            MedicalOfficeDataUpdatableDTO mergedOffice2 = result.get(1);
            assertEquals(102, mergedOffice2.getOfficeNumber(), "El número debe coincidir");
            assertNull(mergedOffice2.getStartDate(), "La fecha de inicio debe ser nula");
            assertNull(mergedOffice2.getEndDate(), "La fecha fin debe ser nula");
        }

        @Test
        @DisplayName("Caso Feliz: Combinar cuando no hay schedules disponibles")
        void mergeMedicalOfficesWithSchedules_WithNoSchedules_ShouldReturnOfficesWithNullDates() {
            // ARRANGE
            MedicalOfficeParamsDTO office = new MedicalOfficeParamsDTO();
            office.setIdOffice(1);
            office.setOfficeNumber(101);
            office.setClinicName("Clinica Central");
            office.setSpecialtyName("Cardiologia");
            office.setStatus("ACTIVE");

            List<MedicalOfficeParamsDTO> offices = Arrays.asList(office);
            List<ScheduleDTO> schedules = Arrays.asList(); // Sin schedules

            // ACT
            List<MedicalOfficeDataUpdatableDTO> result = 
                    medicalOfficeService.mergeMedicalOfficesWithSchedules(offices, schedules);

            // ASSERT
            assertNotNull(result, "El resultado no debe ser nulo");
            assertEquals(1, result.size(), "Debe retornar 1 consultorio");
            assertNull(result.get(0).getStartDate(), "La fecha de inicio debe ser nula");
            assertNull(result.get(0).getEndDate(), "La fecha fin debe ser nula");
        }
    }

    //Final
}
