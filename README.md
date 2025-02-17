# api-medvoll-alura
Projeto desenvolvido durante a formação **Java e Spring Boot**.  
[Construído apenas para consultas]

- Para realizar requisições, a senha precisa ser cadastrada no Banco de Dados em formato **hash**.  
- Você pode gerar a senha utilizando o seguinte site:
[Gerador de Senha Hash](https://bcrypt-generator.com/)

# **Requisições**

## **Autenticação**

Para acessar os endpoints protegidos, é necessário realizar login e utilizar o **token JWT** no cabeçalho das requisições.
- Faça login na API para obter um token:
    - **Endpoint:** 
   ```http
      POST /vollmed/api/login
   ```
    - **Exemplo de requisição:**
   ```json
   {
       "login": "ana.souza.admin@voll.med",
       "senha": "admin"
   }
   ```
   - **Configuração da aba 'Authorization':**
      1. **Auth Type**: OAuth 2.0
      2. **Add authorization data to**: Request Headers
      3. **Current Token**:
         - **Tokens**: Available Tokens
         - **Header Prefix**: Bearer  

## **Médico**
### **Cadastro de Médico**
 - **Endpoint:**
  ```http
     POST /vollmed/api/medicos/cadastrar
  ```
   - **Exemplo de requisição:**
  ```json
  {
    "nome": "Amara Santos",
    "email": "santos.amara@voll.med",
    "telefone": "61997876789",
    "crm": "985669",
    "especialidade": "ORTOPEDIA",
    "endereco": {
        "logradouro": "rua 5",
        "bairro": "bairro s",
        "cep": "12375878",
        "uf": "DF",
        "numero": "1",
        "complemento": "complemento",
        "cidade": "Brasília"
    }
  }
  ```

### **Listar Médicos**
- **Endpoint:**
  ```http
     GET /vollmed/api/medicos/listarMedicos
  ```
  
### **Listar Médicos com Paginação**
- **Endpoint:**
  ```http
     GET /vollmed/api/medicos/listarMedicos?size=1&page=5
  ```
  
### **Listar Médicos com Paginação e Ordenação**
- **Endpoint:**
  ```http
     GET /vollmed/api/medicos/listarMedicos?size=1&page=0&sort=nome
  ```
  
### **Atualizar Médico**
- **Endpoint:**
  ```http
     PUT /vollmed/api/medicos/atualizarMedico
  ```
- **Exemplo de requisição:**
  ```json
  {
    "id" : 11,
    "nome": "Isadora Bernardes Silvana"
  }
  ```
  
### **Desativação Médico - Exclusão Lógica**
- **Endpoint:**
  ```http
     DELETE /vollmed/api/medicos/inativarMedico/7
  ```
  
### **Listar Médicos com Paginação e Ordenação**
- **Endpoint:**
  ```http
     GET /vollmed/api/medicos/listarMedicos?size=1&page=0&sort=nome
  ```  
### **Detalhar Médico**
- **Endpoint:**
  ```http
     GET /vollmed/api/medicos/medico/7
  ```

## **Paciente**
### **Cadastro de Paciente**
- **Endpoint:**
  ```http
     POST /vollmed/api/pacientes/cadastrar
  ```
- **Exemplo de requisição:**
  ```json
  {
    "nome": "Carolina Forbes",
    "email": "car_forbes@email.com",
    "telefone" : "61999527779",
    "cpf": "578.975.458-97",
    "endereco": {
        "logradouro": "rua 12",
        "bairro": "bairro y",
        "cep": "12377698",
        "uf": "GO",
        "numero": "1",
        "complemento": "complemento",
        "cidade": "Goiânia"
    }
  }
  ```
### **Listar Pacientes**
- **Endpoint:**
  ```http
     GET /vollmed/api/pacientes/listarPacientes
  ```
### **Listar Pacientes com Paginação**
- **Endpoint:**
  ```http
     GET /vollmed/api/pacientes/listarPacientes?size=2
  ``` 
### **Atualizar Paciente**
- **Endpoint:**
  ```http
     PUT /vollmed/api/pacientes/atualizarPaciente
  ```
- **Exemplo de requisição:**
  ```json
  {
    "id": 4,
    "telefone": "61896523475"
  }
  ```  
  
### **Inativar Paciente**
- **Endpoint:**
  ```http
     DELETE /vollmed/api/pacientes/inativarPaciente/3
  ```
  
### **Detalhar Paciente**
- **Endpoint:**
  ```http
     GET /vollmed/api/pacientes/paciente/30
  ``` 
  
## **Consulta**

### **Agendar Consulta com idMedico**
- **Endpoint:**
  ```http
     POST /vollmed/api/consultas/agendamento
  ```
- **Exemplo de requisição:**
  ```json
  {
    "idPaciente":2,
    "idMedico":6,
    "data": "2025-02-17T12:40:00"
  }
  ```
  
### **Agendar Consulta sem idMedico**
- **Endpoint:**
  ```http
     POST /vollmed/api/consultas/agendamento
  ```
- **Exemplo de requisição:**
  ```json
  {
    "idPaciente":1,
    "especialidade":"ORTOPEDIA",
    "data": "2025-02-25T15:30:00"
  }
  ```
  - Para agendar sem idMedico, precisa especificar a Especialidade - Enum: EspecialidadeEnum

### **Cancelar Agendamento Consulta**
- **Endpoint:**
  ```http
     DELETE /vollmed/api/consultas/cancelar-agendamento
  ```
- **Exemplo de requisição:**
  ```json
  {
    "idConsulta":6,
    "motivo":"PACIENTE_DESISTIU"
  }
  ```
   - O Motivo precisa estar listado no Enum: MotivoCancelamento