package station.poc.data.models;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ApiResponseModel {
        private int size;
        private List<SensorModel> sensors;

        public ApiResponseModel(List<SensorModel> sensors){
            this.sensors = sensors;
            this.size = sensors != null ? sensors.size() : 0;
        }

        public int getSize(){
            return !sensors.isEmpty() ? sensors.size() : 0;
        }
}
