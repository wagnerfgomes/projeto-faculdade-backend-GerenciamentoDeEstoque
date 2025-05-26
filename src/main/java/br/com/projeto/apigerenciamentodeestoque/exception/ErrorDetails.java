package br.com.projeto.apigerenciamentodeestoque.exception;

public enum ErrorDetails {
    USER_NOT_FOUND("Usuário não existe", 404, "Err-01"),
    ACESS_DENIED("Acesso negado, usuário ou senha inválidos", 401, "Err-02"),
    USER_EXIST("Este usuário já existe!", 409, "Err-03"),
    EMPTY_FIELDS("Os Campos usuário ou senha está vazio", 400, "Err-03"),
    PASSWORD_ALREADY_USED("A nova senha não pode ser igual à anterior.", 409, "Err-04"),
    USER_NOT_ACTIVE("Usuário não está ativo.", 400, "Err-05"),

    PRODUCT_ALREADY_EXISTS("Já existe um produto com esse nome", 409, "Err-06"),
    PRODUCT_NOT_FOUND("produto não encontrado", 404, "Err-07"),
    INVALID_PRODUCT_DATA("Dados do produto inválidos", 400, "Err-08"),
    PRODUCT_LIST_ERROR("Erro ao listar produtos", 500, "Err-010"),
    PRODUCT_ALREADY_DEACTIVATED("Produto já desativado", 400, "Err-011"),

    CATEGORY_PRODUCT_REQUIRED("Categoria é obrigatória", 400, "Err-9"),
    CATEGORY_ALREADY_EXISTS("Já existe uma categoria com esse nome", 409, "Err-"),
    CATEGORY_PRODUCT_NOT_FOUND("Categoria não encontrada", 404, "Err-")
    ;

    private final String message;
    private final int statusCode;
    private final String errCode;

    ErrorDetails(String message, int statusCode, String errCode) {
        this.errCode = errCode;
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getMessage(){
        return message;
    }
}
