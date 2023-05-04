import React, { useEffect, useState } from "react";
import { getLogs } from "../../api/logs";
import Section from "../../components/Section";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";

import "./SqlLogger.css";

const SqlLogger = () => {
  const [logs, setLogs] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    const fetchLogs = async () => {
      const res = await getLogs();
      setLogs(res);
    };

    fetchLogs();
  }, []);

  return (
    <Section bgImage="bg.jpg" height={"100vh"} opacity={0.6}>
      <Paper
        sx={{
          width: "95%",
          overflow: "hidden",
          margin: "auto",
          marginTop: "5%",
          borderRadius: "5px",
          boxShadow: "0 0 10px 0 rgba(0,0,0,0.2)",
          padding: "20px",
          boxSizing: "border-box",
          maxHeight: "100%",
        }}
      >
        <TableContainer sx={{ maxHeight: "100%" }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell><b>Query</b></TableCell>
                <TableCell><b>Type</b></TableCell>
                <TableCell><b>Table Names</b></TableCell>
                <TableCell><b>Parameters</b></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {logs
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((log) => (
                  <TableRow key={log.id}>
                    <TableCell>{log.sql}</TableCell>
                    <TableCell>{log.method}</TableCell>
                    <TableCell>{log.tableName.join(", ")}</TableCell>
                    <TableCell>{log.params.join(", ")}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
        rowsPerPageOptions={[10, 15, 20]}
          component="div"
          count={logs.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={(e, newPage) => setPage(newPage)}
          onRowsPerPageChange={(e) => {
            setRowsPerPage(parseInt(e.target.value, 10));
            setPage(0);
          }}
        />
      </Paper>
    </Section>
  );
};

export default SqlLogger;
