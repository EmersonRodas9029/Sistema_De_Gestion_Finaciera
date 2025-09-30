document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById("chartIngresosGastos");
    if (ctx) {
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [
                    { label: "Ingresos", data: [], backgroundColor: "#0A3D62", borderColor: "#0A3D62", fill: false },
                    { label: "Gastos", data: [], backgroundColor: "#800020", borderColor: "#800020", fill: false }
                ]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    const tables = document.querySelectorAll('.dashboard-table');
    tables.forEach(table => {
        $(table).DataTable({
            paging: true,
            searching: true,
            ordering: true,
            pageLength: 10
        });
    });

    const dropzones = document.querySelectorAll('.dropzone');
    dropzones.forEach(dz => {
        new Dropzone(dz, { url: "/uploads", maxFiles: 5, acceptedFiles: "image/*,application/pdf" });
    });
});
