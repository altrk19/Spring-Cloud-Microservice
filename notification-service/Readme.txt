cloud.stream.bindings.output -> Sender (ticket service)
cloud.stream.bindings.input  -> Listener (notification service)

@EnableBinding(Source.class) -> Sender side
@EnableBinding(Sink.class)   -> Listener side