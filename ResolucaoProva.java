import java.util.Scanner;

class Conta {
    int id;
    String cliente;
    double saldo;
    double limite; 
}

class Operacao {
    int id; 
    int idConta;
    char tipo;
    double valor;
}

public class ResolucaoProva {

    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000; 

    static Scanner sc = new Scanner(System.in); 


public static int buscaId(Conta[] v, int tam, int x) {
    for (int i = 0; i < tam; i++) {
        if (v[i].id == x) {
            return i;
        }
    }
    return -1;
}

public static double LerNaoNegativo (String msg) {
    double valor;

    do { 
        System.out.println(msg);
        valor = sc.nextDouble(); 

        if (valor < 0) {
            System.out.println("O valor não pode ser negativo!");
        }
    } while (valor < 0);
    return valor; 
}

public static int cadastrarConta(Conta[] v, int tam) {
    if (tam >= TAM_CONTAS) {
        System.out.println("Vetor de contas cheio!");
        return tam; 
    }

    System.out.print("ID: ");
    int id = sc.nextInt(); 
    if (buscaId(v, tam, id) != -1) {
        System.out.println("ID já cadastrado!"); 
        return tam; 
    }

    sc.nextLine(); 
    System.out.print("Cliente: ");
    String cliente = sc.nextLine(); 
    double saldo = LerNaoNegativo("Saldo: "); 
    double limite = LerNaoNegativo("Limite: "); 


    v[tam] = new Conta(); 
    v[tam].id = id; 
    v[tam].cliente = cliente; 
    v[tam].saldo = saldo;
    v[tam].limite = limite; 

    return tam + 1; 
}

public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
    int inicio = 0;
    int fim = tam - 1; 

    while (inicio <= fim) { 
        int meio = (inicio + fim) / 2; 
        int cmp = v[meio].cliente.compareTo(x); 

        if (cmp == 0) {
            return meio; 
        }
        else if (cmp < 0) {
            inicio = meio + 1; 
        } else {
            fim = meio - 1; 
        }
    }
    return -1; 
}

public static int insertionSortCliente(Conta [] v, int tam) {
    for (int i = 1; i < tam; i++) {
        Conta chave = v[i];
        int j = i - 1;

        while (j >= 0 && v[j].cliente.compareTo(chave.cliente) > 0) {
            v[j + 1] = v[j]; 
            j--; 
        }
        v[j + 1] = chave;
        
    }
    return tam; 
}

public static int filtraOperacoes(Operacao[] vOp, int tamOp, Operacao[] vOpFiltrado, int x) {
    int tamFiltrado = 0; 
    for (int i = 0; i < tamOp; i++) {
        if (vOp[i].idConta == x) {
            vOpFiltrado[tamFiltrado] = vOp[i]; 
            tamFiltrado++; 
        }
    }
    return tamFiltrado; 
}

public static void extrato(Conta[] vContas, int tamConta, Operacao[] vOp, int tamOp) {
        System.out.print("Digite o ID da conta para o extrato: ");
        int idBusca = sc.nextInt();

        int posConta = buscaId(vContas, tamConta, idBusca);
        if (posConta == -1) {
            System.out.println("Conta não encontrada!");
            return;
        }
        Conta c = vContas[posConta];

        
        Operacao[] vOpFiltrada = new Operacao[tamOp];
        int tamFiltrado = filtraOperacoes(vOp, tamOp, vOpFiltrada, idBusca);

        int inicio = 0;
        if (tamFiltrado > 5) {
            inicio = tamFiltrado - 5;
        }

        
        double saldoAnterior = 0;
        for (int i = 0; i < inicio; i++) {
            saldoAnterior += vOpFiltrada[i].valor;
        }

        
        char dcAnterior = (saldoAnterior >= 0) ? 'C' : 'D';

        System.out.println("Conta N." + c.id);
        System.out.println("Cliente: " + c.cliente);
        System.out.println("");
        System.out.println("ID\tOPERAÇÃO\tVALOR\t\tSALDO");
        
        System.out.printf("\tSaldo Anterior\t\t\t%.2f%c\n",
                Math.abs(saldoAnterior), dcAnterior);

        double saldoAtual = saldoAnterior;
        for (int i = inicio; i < tamFiltrado; i++) {
            Operacao op = vOpFiltrada[i];

            
            String nomeOp = "";
            if (op.tipo == 'D') {
                nomeOp = "Depósito";
            } else if (op.tipo == 'S') {
                nomeOp = "Saque";
            } else {
                nomeOp = "Transferência";
            }

            saldoAtual += op.valor;
            char dcValor = (op.valor >= 0) ? 'C' : 'D';
            char dcSaldo = (saldoAtual >= 0) ? 'C' : 'D';

            
            System.out.printf("%d\t%s\t%.2f%c\t\t%.2f%c\n",
                    op.id, nomeOp, Math.abs(op.valor), dcValor,
                    Math.abs(saldoAtual), dcSaldo);
        }

        
        char dcFinal = (saldoAtual >= 0) ? 'C' : 'D';
        System.out.println();
        System.out.printf("Saldo Atual: %.2f%c\n", Math.abs(saldoAtual), dcFinal);
    }

    
public static void main(String[] args) {
    Conta[] contas = new Conta[TAM_CONTAS];
    Operacao[] ops = new Operacao[TAM_OP];
    int tamContas = 0;
    int tamOps = 0;

    
    double[] valores = {2437.55, 1000.00, 237.44, -4000.00, -250.00, 1000.00};
    char[] tipos = {'D', 'D', 'D', 'T', 'S', 'T'};
    int[] ids = {10, 25, 37, 83, 95, 112};

    for (int i = 0; i < valores.length; i++) {
        ops[tamOps] = new Operacao();
        ops[tamOps].id = ids[i];
        ops[tamOps].idConta = 1;
        ops[tamOps].tipo = tipos[i];
        ops[tamOps].valor = valores[i];
        tamOps++;
    }

    int opcao;
    do {
        System.out.println("\n1 - Cadastrar conta");
        System.out.println("2 - Ordenar contas por cliente");
        System.out.println("3 - Buscar cliente (busca binária)");
        System.out.println("4 - Extrato");
        System.out.println("0 - Sair");
        System.out.print("Opção: ");
        opcao = sc.nextInt();
        sc.nextLine();

        if (opcao == 1) {
            tamContas = cadastrarConta(contas, tamContas);
        } else if (opcao == 2) {
            insertionSortCliente(contas, tamContas);
            System.out.println("Contas ordenadas!");
        } else if (opcao == 3) {
            System.out.print("Nome do cliente: ");
            String nome = sc.nextLine();
            int pos = buscaBinariaCliente(contas, tamContas, nome);
            if (pos == -1) {
                System.out.println("Cliente não encontrado!");
            } else {
                System.out.println("Encontrado na posição " + pos);
            }
        } else if (opcao == 4) {
            extrato(contas, tamContas, ops, tamOps);
        }
    } while (opcao != 0);
    }
}