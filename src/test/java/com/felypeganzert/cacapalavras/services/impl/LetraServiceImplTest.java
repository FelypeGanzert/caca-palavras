package com.felypeganzert.cacapalavras.services.impl;

public class LetraServiceImplTest {
    
    // @Test
    // void deveLimparTodasAsLetrasDoTabuleiroComSucesso() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);

    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    // }

    // @Test
    // void deveLimparTodasAsLetrasDoTabuleiroEAsLocalizacoesDasPalavrasComSucesso() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
    //     assertThat(cacaPalavras.getPalavras()).isNotEmpty();
    //     for (Palavra palavra : cacaPalavras.getPalavras()) {
    //         assertThat(palavra.getLocalizacoesNoTabuleiro()).isNotEmpty();
    //     }
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);

    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    //     assertThat(cacaPalavras.getPalavras()).isNotEmpty();
    //     for (Palavra palavra : cacaPalavras.getPalavras()) {
    //         assertThat(palavra.getLocalizacoesNoTabuleiro()).isEmpty();
    //     }
    // }

    // @Test
    // void naoDeveGerarExceptionAoLimparAsLetrasDoTabuleiroQueNaoContemLetras() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasNaoLocalizadasESemLetrasNoTabuleiro();
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);
    // }

    // @Test
    // void deveChamarSaveDoRepositoryComSucessoAposLimparLetras() {
    //     CacaPalavras cacaPalavras = CacaPalavrasCreator.criarComPalavrasLocalizadas(tabuleiroService);
    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isNotEmpty();
    //     deveRetornarIssoQuandoRepositoryFindByIdForChamado(cacaPalavras);

    //     service.limparLetrasDoTabuleiro(ID_CACA_PALAVRAS);

    //     assertThat(cacaPalavras.getTabuleiro().getLetras()).isEmpty();
    //     Mockito.verify(repository).save(Mockito.any(CacaPalavras.class));
    // }

    // @Test
    // void deveAdicionarLetrasComSucesso() {
    // Integer idTabuleiro = 1;
    // List<Letra> letras = new ArrayList<>();
    // Letra a = new Letra(1, "a", new Posicao(1, 1));
    // Letra b = new Letra(1, "a", new Posicao(1, 2));
    // letras.add(a);
    // letras.add(b);

    // tabuleiroService.adicionarLetras(idTabuleiro, letras);
    // }

    // @Test
    // void deveInserirLetraEmCelulaComSucessoEmPosicaoInicialDoTabuleiro() {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Posicao posicaoInicial = new Posicao(1, 1);
    // Letra a = new Letra(1, "a", posicaoInicial);

    // tabuleiroService.inserirLetra(tabuleiro, a);

    // assertThat(tabuleiro.getLetras()).contains(a);
    // }

    // @Test
    // void deveInserirLetraEmCelulaComSucessoEmPosicaoNoExtremoDoTabuleiro() {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Posicao posicaoNoExtremo = new Posicao(tabuleiro.getLargura(),
    // tabuleiro.getAltura());
    // Letra a = new Letra(1, "a", posicaoNoExtremo);

    // tabuleiroService.inserirLetra(tabuleiro, a);

    // assertThat(tabuleiro.getLetras()).contains(a);
    // }

    // @Test
    // void deveGerarIllegalStateExceptionAoInserirLetraEmPosicaoForaDoTabuleiro() {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Posicao posicaoFora = new Posicao(tabuleiro.getLargura() + 1,
    // tabuleiro.getAltura() + 1);
    // Letra a = new Letra(1, "a", posicaoFora);

    // assertThatExceptionOfType(IllegalStateException.class)
    // .isThrownBy(() -> tabuleiroService.inserirLetra(tabuleiro, a))
    // .withMessage("Posição desejada para inserir não existe no tabuleiro");

    // assertThat(tabuleiro.getLetras()).doesNotContain(a);
    // }

    // @Test
    // void
    // deveGerarIllegalStateExceptionAoInserirLetraEmPosicaoComXForaDoTabuleiro() {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Posicao posicaoXFora = new Posicao(tabuleiro.getLargura() + 1,
    // tabuleiro.getAltura());
    // Letra a = new Letra(1, "a", posicaoXFora);

    // assertThatExceptionOfType(IllegalStateException.class)
    // .isThrownBy(() -> tabuleiroService.inserirLetra(tabuleiro, a))
    // .withMessage("Posição desejada para inserir não existe no tabuleiro");

    // assertThat(tabuleiro.getLetras()).doesNotContain(a);
    // }

    // @Test
    // void
    // deveGerarIllegalStateExceptionAoInserirLetraEmPosicaoComYForaDoTabuleiro() {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Posicao posicaoYFora = new Posicao(tabuleiro.getLargura(),
    // tabuleiro.getAltura() + 1);
    // Letra a = new Letra(1, "a", posicaoYFora);

    // assertThatExceptionOfType(IllegalStateException.class)
    // .isThrownBy(() -> tabuleiroService.inserirLetra(tabuleiro, a))
    // .withMessage("Posição desejada para inserir não existe no tabuleiro");

    // assertThat(tabuleiro.getLetras()).doesNotContain(a);
    // }

    // @Test
    // void deveInserirMaisDeUmaLetraComSucessoEmPosicoesVazia() {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Letra a = new Letra(1, "a", new Posicao(1, 1));
    // Letra b = new Letra(2, "b", new Posicao(1, 2));

    // tabuleiroService.inserirLetra(tabuleiro, a);
    // tabuleiroService.inserirLetra(tabuleiro, b);

    // assertThat(tabuleiro.getLetras()).contains(a, b);
    // }

    // @Test
    // void
    // deveLimparValorAnteriorDaCelulaEInserirNovaLetraComSucessoEmPosicaoQueJaPossuiaUmaLetra()
    // {
    // Tabuleiro tabuleiro = criarTabuleiroValido();
    // Letra a = new Letra(1, "a", new Posicao(1, 1));
    // Letra b = new Letra(2, "b", new Posicao(1, 2));
    // tabuleiroService.inserirLetra(tabuleiro, a);
    // tabuleiroService.inserirLetra(tabuleiro, b);

    // assertThat(tabuleiro.getLetras()).contains(a, b);
    // assertThat(tabuleiro.getLetras()).size().isEqualTo(2);

    // Posicao posicaoB = new Posicao(1, 2);
    // Letra c = new Letra(3, "c", posicaoB);
    // tabuleiroService.inserirLetra(tabuleiro, c);

    // assertThat(tabuleiro.getLetras()).contains(a, c).doesNotContain(b);
    // assertThat(tabuleiro.getLetras()).size().isEqualTo(2);
    // }

}
