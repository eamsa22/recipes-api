package communs;

import jakarta.ws.rs.core.Response;

public enum ResponseAPI {
    INTERNAL_SERVER_ERROR,
    BAD_REQUEST,
    OK;

    String response;
    String apiFailed;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setApiFailed(String apiFailed) {
        this.apiFailed = apiFailed;
    }

    public String getApiFailed() {
        return apiFailed;
    }

    public String getResponse() {
        return response;
    }

    public static int getStatusCode(ResponseAPI responseApi) {
        switch (responseApi) {
            case OK:
                return 200 ;
            case INTERNAL_SERVER_ERROR:
                return  500 ;
            case BAD_REQUEST:
                return 400 ;
            default:
                throw new IllegalArgumentException("reponse inconnue " +responseApi);
        }
    }
}