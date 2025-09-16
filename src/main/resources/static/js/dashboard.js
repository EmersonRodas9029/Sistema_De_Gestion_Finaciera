document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/resumen/mensual')
        .then(resp => resp.ok ? resp.json() : Promise.reject(resp))
        .then(data => {
            const ctx = document.getElementById('balanceChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: data.labels,
                    datasets: [
                        { label: 'Ingresos', data: data.ingresos, fill: false, tension: 0.3 },
                        { label: 'Gastos',  data: data.gastos, fill: false, tension: 0.3 }
                    ]
                },
                options: {
                    responsive: true,
                    plugins: { legend: { position: 'top' } }
                }
            });

            document.getElementById('summaryIncomes').textContent = '$' + (data.totalIngresos || 0).toFixed(2);
            document.getElementById('summaryExpenses').textContent = '$' + (data.totalGastos || 0).toFixed(2);
            document.getElementById('summaryBalance').textContent = '$' + ((data.totalIngresos || 0) - (data.totalGastos || 0)).toFixed(2);
        })
        .catch(err => console.error('Error cargando resumen mensual', err));

    fetch('/api/gastos?limit=10')
        .then(r => r.json())
        .then(gastos => {
            const tbody = document.querySelector('#recentExpenses') || document.createElement('div');
            if (tbody.tagName === 'DIV') return;
            gastos.forEach(g => {
                const tr = document.createElement('tr');
                tr.innerHTML = `<td>${g.id}</td>
                        <td>${g.monto}</td>
                        <td>${g.fecha}</td>
                        <td>${g.categoriaId || '-'}</td>
                        <td>${g.metodoPago}</td>`;
                tbody.appendChild(tr);
            });
        }).catch(() => {});
});
