package pl.mirbudpol.sklepbudowlany.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mirbudpol.sklepbudowlany.DTO.ReportFirstDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RaportRequestDTO;
import pl.mirbudpol.sklepbudowlany.services.ReportsService;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @ApiOperation("Zwraca listę zamówień pomiędzy dwoma datami")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RaportRequestDTO getReportFirst(@Validated @RequestBody ReportFirstDTO dto) {
        return reportsService.getReportFirst(dto);
    }
}
