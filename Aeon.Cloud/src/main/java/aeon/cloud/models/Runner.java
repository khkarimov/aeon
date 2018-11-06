package aeon.cloud.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Runner entity.
 */
@Document(collection = "aeonCloudRunners")
public class Runner {

    @Id
    public String id;

    @NotEmpty
    public String name;

    @NotEmpty
    public String tenant;

    @NotEmpty
    public String status;

    @Nullable
    public String apiUrl;

    @Nullable
    public String uiUrl;

    @Nullable
    public String baseUrl;

    @NotNull
    public PCFMetaData pcfMetaData = new PCFMetaData();

    /**
     * Constructor.
     *
     * @param id     The ID of the runner.
     * @param name   The name of the runner.
     * @param tenant The tenant the runner belongs to.
     */
    public Runner(String id, String name, String tenant) {
        this.id = id;
        this.name = name;
        this.tenant = tenant;
        this.status = "PENDING";
    }
}
