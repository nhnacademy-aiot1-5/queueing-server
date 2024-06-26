package live.ioteatime.bridgeserver.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@ConditionalOnProperty(name = "bridge.mode", havingValue = "mqtt")
public class MqttClientCloseListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
            try {
                MqttClient mqttClient = event.getApplicationContext().getBean(MqttClient.class);
                mqttClient.disconnect();
                mqttClient.close();
            } catch (MqttException e) {
                log.info("Mqtt client is alvie {}",e.getMessage());
            }
        log.info("mqtt client disconnected");
    }
}
