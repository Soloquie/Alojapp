package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAdministradorRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAdministradorRequest;
import co.uniquindio.alojapp.negocio.Service.AdministradorService;
import co.uniquindio.alojapp.seguridad.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AdminController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    AdministradorService adminService;

    @Autowired
    ObjectMapper objectMapper;

    // Helpers para construir DTOs de prueba
    private AdministradorDTO adminDto(Integer id, Integer usuarioId, String nivel) {
        AdministradorDTO dto = new AdministradorDTO();
        dto.setId(id);
        dto.setId(usuarioId);
        dto.setNivelAcceso(nivel);
        dto.setPermisosJson("{\"puedeBloquear\":true}");
        dto.setFechaAsignacion(LocalDateTime.now());
        return dto;
    }


    @Test
    @DisplayName("PUT /api/admin/administradores/{id} -> 200 OK")
    void actualizarAdministrador_deberiaActualizar() throws Exception {
        ActualizarAdministradorRequest req = new ActualizarAdministradorRequest();
        req.setNivelAcceso("SUPER_ADMIN");
        req.setPermisosJson("{\"todo\":true}");

        AdministradorDTO resp = adminDto(1, 10, "SUPER_ADMIN");

        Mockito.when(adminService.actualizar(eq(1), (ActualizarAdministradorRequest) any(ActualizarAdministradorRequest.class)))
                .thenReturn(resp);

        mvc.perform(put("/api/admin/administradores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nivelAcceso", is("SUPER_ADMIN")));
    }

    @Test
    @DisplayName("DELETE /api/admin/administradores/usuario/{usuarioId} -> 204 No Content")
    void revocarAdministrador_deberiaSerNoContent() throws Exception {
        Mockito.doNothing().when(adminService).revocarPorUsuario(10);

        mvc.perform(delete("/api/admin/administradores/usuario/10"))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("GET /api/admin/administradores -> 200 lista")
    void listarAdministradores_deberiaRetornarLista() throws Exception {
        Mockito.when(adminService.listar())
                .thenReturn(List.of(
                        adminDto(1, 10, "ADMIN"),
                        adminDto(2, 11, "MODERADOR")
                ));

        mvc.perform(get("/api/admin/administradores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nivelAcceso", is("ADMIN")))
                .andExpect(jsonPath("$[1].nivelAcceso", is("MODERADOR")));
    }
}
