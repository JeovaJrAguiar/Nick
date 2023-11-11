package api.nick.service;

import api.nick.entity.dataset.DataSet;
import api.nick.entity.dataset.Intents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NickBotService {
    private static final NaiveBayes classifier = new NaiveBayes();
    private static DataSet dataSet;

    public static void training() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            dataSet = mapper.readValue(new File("C:\\Users\\soare\\Downloads\\questoes.json"), DataSet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Instances trainingData = new Instances("training", createAttributes(), 10000);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        dataSet.intents.forEach(item -> {
            item.patterns.forEach(pattern -> {
                addPergunta(trainingData, pattern, item.tag);
            });
        });

        classifier.buildClassifier(trainingData);
    }

    private static void addPergunta(Instances data, String pergunta, String classe) {
        Instance inst = new DenseInstance(data.numAttributes());
        List<String> palavraschaves = getPalavrasChave(classe);
        palavraschaves.forEach(x -> {
            inst.setValue(data.attribute("palavra-chave"), pergunta.toLowerCase().contains(x) ? 1 : 0);
        });
        inst.setValue(data.attribute("comprimento"), pergunta.length());
        inst.setValue(data.attribute("classe"), classe);
        inst.setDataset(data);
        data.add(inst);
    }

    public static String classificarPergunta(String pergunta) throws Exception {
        Instances data = new Instances("classification", createAttributes(), 1000);
        data.setClassIndex(data.numAttributes() - 1);

        Instance inst = new DenseInstance(data.numAttributes());

        List<String> palavraschaves = getPalavrasChave();

        palavraschaves.forEach(x -> {
            inst.setValue(data.attribute("palavra-chave"), pergunta.toLowerCase().contains(x) ? 1 : 0);
        });

        inst.setValue(data.attribute("comprimento"), pergunta.length());
        inst.setDataset(data);

        double predicao = classifier.classifyInstance(inst);
        String tag = inst.classAttribute().value((int) predicao);
        String response = null;
        for (Intents intents : dataSet.intents)
            if (tag.equals(intents.tag))
                response = intents.responses.get(0);

        return response;
    }

    private static ArrayList<String> getClasses() {
        ArrayList<String> classes = new ArrayList<>();
        classes.add("greeting");
        classes.add("goodbye");
        classes.add("creator");
        classes.add("name");
        classes.add("hours");
        classes.add("number");
        classes.add("course");
        classes.add("fees");
        classes.add("location");
        classes.add("hostel");
        classes.add("event");
        classes.add("document");
        classes.add("floors");
        classes.add("syllabus");
        classes.add("library");
        classes.add("infrastructure");
        classes.add("canteen");
        classes.add("menu");
        classes.add("placement");
        classes.add("principal");
        classes.add("sem");
        classes.add("admission");
        classes.add("scholarship");
        classes.add("facilities");
        classes.add("college intake");
        classes.add("uniform");
        classes.add("committee");
        classes.add("random");
        classes.add("swear");
        classes.add("vacation");
        classes.add("sports");
        classes.add("salutaion");
        classes.add("task");
        return classes;
    }

    private static ArrayList<String> getPalavrasChave() {
        ArrayList<String> palavraschaves = new ArrayList<>();
        palavraschaves.add("oi");
        palavraschaves.add("ola");
        palavraschaves.add("bom dia");
        palavraschaves.add("boa tarde");
        palavraschaves.add("boa noite");
        palavraschaves.add("tudo bem");
        palavraschaves.add("obrigado");
        palavraschaves.add("tchau");
        palavraschaves.add("cria");
        palavraschaves.add("seus");
        palavraschaves.add("nome");
        palavraschaves.add("horário");
        palavraschaves.add("abre");
        palavraschaves.add("número");
        palavraschaves.add("contato");
        palavraschaves.add("telefone");
        palavraschaves.add("cursos");
        palavraschaves.add("curso");
        palavraschaves.add("taxas");
        palavraschaves.add("taxa");
        palavraschaves.add("endereço");
        palavraschaves.add("onde");
        palavraschaves.add("albergue");
        palavraschaves.add("eventos");
        palavraschaves.add("evento");
        palavraschaves.add("documentos");
        palavraschaves.add("documento");
        palavraschaves.add("tamanho");
        palavraschaves.add("andares");
        palavraschaves.add("programa");
        palavraschaves.add("biblioteca");
        palavraschaves.add("livros");
        palavraschaves.add("infraestrutura");
        palavraschaves.add("refeitorio");
        palavraschaves.add("cantina");
        palavraschaves.add("comida");
        palavraschaves.add("cardápio");
        palavraschaves.add("empresas");
        palavraschaves.add("colocação");
        palavraschaves.add("diretor");
        palavraschaves.add("exames");
        palavraschaves.add("exame");
        palavraschaves.add("admissão");
        palavraschaves.add("ingresso");
        palavraschaves.add("bolsa");
        palavraschaves.add("bolsas");
        palavraschaves.add("facilidades");
        palavraschaves.add("instalações");
        palavraschaves.add("assentos");
        palavraschaves.add("número máximo");
        palavraschaves.add("uniforme");
        palavraschaves.add("comitê");
        palavraschaves.add("voçê quer");
        palavraschaves.add("eu te");
        palavraschaves.add("porra");
        palavraschaves.add("idiota");
        palavraschaves.add("inferno");
        palavraschaves.add("férias");
        palavraschaves.add("feriados");
        palavraschaves.add("jogos");
        palavraschaves.add("esportes");
        palavraschaves.add("ok");
        palavraschaves.add("obrigado p");
        palavraschaves.add("pode fazer");
        palavraschaves.add("pode me");
        palavraschaves.add("ajuda");
        return palavraschaves;
    }

    private static ArrayList<String> getPalavrasChave(String tag) {
        ArrayList<String> palavraschaves = new ArrayList<>();
        switch (tag) {
            case "greeting" -> {
                palavraschaves.add("oi");
                palavraschaves.add("ola");
                palavraschaves.add("bom dia");
                palavraschaves.add("boa tarde");
                palavraschaves.add("boa noite");
                palavraschaves.add("tudo bem");
            }
            case "goodbye" -> {
                palavraschaves.add("obrigado");
                palavraschaves.add("tchau");
            }
            case "creator" -> {
                palavraschaves.add("cria");
                palavraschaves.add("seus");
            }
            case "name" -> {
                palavraschaves.add("nome");
            }
            case "hours" -> {
                palavraschaves.add("horário");
                palavraschaves.add("abre");
            }
            case "number" -> {
                palavraschaves.add("número");
                palavraschaves.add("contato");
                palavraschaves.add("telefone");
            }
            case "course" -> {
                palavraschaves.add("cursos");
                palavraschaves.add("curso");
            }
            case "fees" -> {
                palavraschaves.add("taxas");
                palavraschaves.add("taxa");
            }
            case "location" -> {
                palavraschaves.add("endereço");
                palavraschaves.add("onde");
            }
            case "hostel" -> {
                palavraschaves.add("albergue");
            }
            case "event" -> {
                palavraschaves.add("eventos");
                palavraschaves.add("evento");
            }
            case "document" -> {
                palavraschaves.add("documentos");
                palavraschaves.add("documento");
            }
            case "floors" -> {
                palavraschaves.add("tamanho");
                palavraschaves.add("andares");
            }
            case "syllabus" -> {
                palavraschaves.add("programa");
            }
            case "library" -> {
                palavraschaves.add("biblioteca");
                palavraschaves.add("livros");
            }
            case "infrastructure" -> {
                palavraschaves.add("infraestrutura");
            }
            case "canteen" -> {
                palavraschaves.add("refeitorio");
                palavraschaves.add("cantina");
            }
            case "menu" -> {
                palavraschaves.add("comida");
                palavraschaves.add("cardápio");
            }
            case "placement" -> {
                palavraschaves.add("empresas");
                palavraschaves.add("colocação");
            }
            case "principal" -> {
                palavraschaves.add("diretor");
            }
            case "sem" -> {
                palavraschaves.add("exames");
                palavraschaves.add("exame");
            }
            case "admission" -> {
                palavraschaves.add("admissão");
                palavraschaves.add("ingresso");
            }
            case "scholarship" -> {
                palavraschaves.add("bolsa");
                palavraschaves.add("bolsas");
            }
            case "facilities" -> {
                palavraschaves.add("facilidades");
                palavraschaves.add("instalações");
            }
            case "college intake" -> {
                palavraschaves.add("assentos");
                palavraschaves.add("número máximo");
            }
            case "uniform" -> {
                palavraschaves.add("uniforme");
            }
            case "committee" -> {
                palavraschaves.add("comitê");
            }
            case "random" -> {
                palavraschaves.add("voçê quer");
                palavraschaves.add("eu te");
            }
            case "swear" -> {
                palavraschaves.add("porra");
                palavraschaves.add("idiota");
                palavraschaves.add("inferno");
            }
            case "vacation" -> {
                palavraschaves.add("férias");
                palavraschaves.add("feriados");
            }
            case "sports" -> {
                palavraschaves.add("jogos");
                palavraschaves.add("esportes");
            }
            case "salutaion" -> {
                palavraschaves.add("ok");
                palavraschaves.add("obrigado p");
            }
            case "task" -> {
                palavraschaves.add("pode fazer");
                palavraschaves.add("pode me");
                palavraschaves.add("ajuda");
            }
        }
        return palavraschaves;
    }

    private static ArrayList<Attribute> createAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("palavra-chave"));
        attributes.add(new Attribute("comprimento"));
        attributes.add(new Attribute("classe", getClasses()));
        return attributes;
    }
}
