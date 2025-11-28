package com.pedro.HealthyPredict.service;

import com.pedro.HealthyPredict.model.Alimento;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.TreeMap;

@Service
public class ArvoreBinariaService {

    private TreeMap<String, Alimento> alimentos = new TreeMap<>();

    public void inserir(Alimento a) {
        alimentos.put(a.getNome().toLowerCase(), a);
    }

    public Alimento buscar(String nome) {
        return alimentos.get(nome.toLowerCase());
    }


    public void carregarExcel() {
        try (InputStream is = getClass().getResourceAsStream("/HealthyPredict.xlsx");
             Workbook workbook = new XSSFWorkbook(is)) {

            if (is == null) {
                throw new RuntimeException("Arquivo HealthyPredict.xlsx não encontrado em resources!");
            }

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Alimento a = new Alimento(
                        getStringCellValue(row, 0), // Nome
                        getStringCellValue(row, 1), // Grupo
                        getStringCellValue(row, 2), // Preparo/Estado
                        getDoubleCellValue(row, 3), // Calorias
                        getDoubleCellValue(row, 4), // Carboidratos
                        getDoubleCellValue(row, 5), // Proteínas
                        getDoubleCellValue(row, 6), // Gorduras Totais
                        getStringCellValue(row, 7)  // Classificação
                );

                inserir(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar o arquivo HealthyPredict.xlsx", e);
        }
    }

    private double getDoubleCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return 0.0;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().replace(",", "."));
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }

    private String getStringCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return "";
        return cell.toString().trim();
    }
}
