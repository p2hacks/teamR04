if has('vim_starting')
    " 初回起動時のみruntimepathにNeoBundleのパスを指定する
    set runtimepath+=`HOME`/.vim/bundle/neobundle.vim/

    " NeoBundleが未インストールであればgit cloneする・・・・・・①
    if !isdirectory(expand("`HOME`/.vim/bundle/neobundle.vim/"))
        echo "install NeoBundle..."
        :call system("git clone git://github.com/Shougo/neobundle.vim `HOME`/.vim/bundle/neobundle.vim")
    endif
endif

call neobundle#begin(expand('`HOME`/.vim/bundle/'))
" インストールするVimプラグインを以下に記述
function! ProcessingRun()
	!prun
endfunction

noremap <Plug>(processing_run) : <C-u>call ProcessingRun()<CR>

NeoBundleFetch 'shougo/neobundle.vim'

NeoBundle 'sophacles/vim-processing'
NeoBundle 'tomasr/molokai'
NeoBundle 'itchyny/lightline.vim'
NeoBundle 'sophacles/vim-processing'

call neobundle#end()

inoremap <C-e> <Esc>&
inoremap <C-a> <Esc>^
noremap <C-e> <Esc>$
noremap <C-a> <Esc>^

noremap ; :
noremap ZQ <Nop>

imap <C-r> <Plug>(processing_run)
map <C-r> <Plug>(processing_run)

inoremap <C-Right> :wnext<CR>
noremap <C-Right> :wnext<CR>


inoremap <C-Left> :wprevious<CR>
noremap <C-Left> :wprevious<CR>

noremap <C-w> :w<CR>

set fenc=utf-8
scriptencoding utf-8
set nobackup
set noswapfile
set autoread
set hidden
set showcmd
set showmatch
set number
set cursorline
set cursorcolumn
set smartindent
set autoindent
set showmatch
set wildmode=list:longest
set hlsearch
set list listchars=tab:\-\>
"set expandtab"
set tabstop=2
set shiftwidth=2
syntax on
set t_Co=256
colorscheme molokai

highlight Normal ctermbg=NONE guibg=NONE
highlight NonText ctermbg=NONE guibg=NONE
highlight SpecialKey ctermbg=NONE guibg=NONE
highlight EndOfBuffer ctermbg=NONE guibg=NONE
highlight LineNr ctermbg=none
highlight CoursorLineNr ctermbg=NONE
highlight TablineSel ctermbg=NONE
