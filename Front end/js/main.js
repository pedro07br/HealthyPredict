async function enviarDados() {
    // Monta o objeto do usuário
    const usuario = {
        idade: parseInt(document.getElementById("idade").value),
        altura: parseFloat(document.getElementById("altura").value),
        peso: parseFloat(document.getElementById("peso").value),
        sexo: document.getElementById("sexo").value,
        objetivo: document.getElementById("objetivo").value
    };

    // Validação básica
    if (!usuario.idade || !usuario.altura || !usuario.peso || !usuario.sexo || !usuario.objetivo) {
        alert("Preencha todos os campos!");
        return;
    }

    try {
        // Chama o backend Java
        const response = await fetch("http://localhost:8080/api/rotina/gerar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuario)
        });

        // Recebe a rotina como JSON
        const rotina = await response.json();

        // Salva no sessionStorage para próxima tela
        sessionStorage.setItem("rotinaAlimentar", JSON.stringify(rotina));

        // Redireciona para a tela de rotina
        window.location.href = "tela_rotina.html";

    } catch (error) {
        console.error("Erro ao gerar rotina:", error);
        alert("Ocorreu um erro ao gerar a rotina. Tente novamente.");
    }
}
